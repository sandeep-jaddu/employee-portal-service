package com.example.employee.service;

import com.example.employee.dto.RegisterResponseDTO;
import com.example.employee.dto.UserDTO;
import com.example.employee.entity.User;
import com.example.employee.mapper.UserMapper;
import com.example.employee.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;





@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JavaMailSender mailSender;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public RegisterResponseDTO saveUser(UserDTO userDTO)
    {
        RegisterResponseDTO registerResponseDTO = new RegisterResponseDTO();
        try{
            if(repo.existsByUsername(userDTO.getUsername()))
                return new RegisterResponseDTO(null,"username already exists",false);
            if(userDTO.getUsername() == null || userDTO.getPassword() == null)
                return new RegisterResponseDTO(null,"username/password should not be null",false);
            if(repo.existsByEmailId(userDTO.getEmailId()))
                return new RegisterResponseDTO(null,"emailId already exists",false);
            userDTO.setPassword(encoder.encode(userDTO.getPassword()));
            User user = new User(userDTO.getId(), userDTO.getUsername(), userDTO.getPassword(), userDTO.getEmailId());
            User saveUserDTO = repo.save(user);
            UserDTO saveUser = new UserDTO(saveUserDTO.getId(), saveUserDTO.getUsername(), saveUserDTO.getPassword(), saveUserDTO.getEmailId());
            registerResponseDTO = new RegisterResponseDTO(saveUser, "User Registered Successfully",true);
            if (registerResponseDTO.isResponseStatus())
            {
                sendHtmlEmail(saveUser.getEmailId(),"Employee Portal : Account Created",getEmailContent());
            }
        }catch (Exception ex)
        {
            registerResponseDTO = new RegisterResponseDTO(null, ex.getMessage(),false);

        }
        return registerResponseDTO;
    }


    public void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // true -> Send as HTML
        helper.setFrom("sandeepnaiducode@gmail.com");

        mailSender.send(message);
        System.out.println("HTML Email sent successfully to " + to);
    }

    public String getEmailContent()
    {
        String emailContent = "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <title>Registration Successful</title>" +
                "    <style>" +
                "        body {" +
                "            font-family: Arial, sans-serif;" +
                "            text-align: center;" +
                "            background-color: #f4f4f4;" +
                "            margin: 0;" +
                "            padding: 0;" +
                "        }" +
                "        .container {" +
                "            width: 50%;" +
                "            margin: 100px auto;" +
                "            padding: 20px;" +
                "            background: white;" +
                "            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);" +
                "            border-radius: 10px;" +
                "        }" +
                "        h1 {" +
                "            color: #4CAF50;" +
                "        }" +
                "        p {" +
                "            font-size: 18px;" +
                "            color: #333;" +
                "        }" +
                "        .button {" +
                "            display: inline-block;" +
                "            padding: 10px 20px;" +
                "            font-size: 18px;" +
                "            color: white;" +
                "            background-color: #4CAF50;" +
                "            text-decoration: none;" +
                "            border-radius: 5px;" +
                "            margin-top: 20px;" +
                "        }" +
                "        .button:hover {" +
                "            background-color: #45a049;" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class=\"container\">" +
                "        <h1>Registration Successful!</h1>" +
                "        <p>Thank you for registering with our application.</p>" +
                "        <p>Your account has been created successfully. You can now log in and start using our services.</p>" +
                "        <a href=\"login.html\" class=\"button\">Go to Login</a>" +
                "    </div>" +
                "</body>" +
                "</html>";
        return emailContent;
    }


    public List<UserDTO> getAllUsers()
    {
        return repo.findAll().stream().map(user -> new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmailId(),
                user.getPassword()
        )).toList();
    }
}
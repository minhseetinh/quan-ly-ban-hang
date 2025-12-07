package view;

import service.UserService;
import utils.AppConstants;
import utils.DialogUtils;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtUser;
    private JPasswordField txtPass;

    public LoginFrame() {
        setTitle("Đăng Nhập - QLBH");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(AppConstants.LIGHT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblTitle = new JLabel("QUẢN LÝ BÁN HÀNG", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(AppConstants.PRIMARY);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(lblTitle, gbc);

        JLabel lblUser = new JLabel("Tên đăng nhập:");
        lblUser.setFont(AppConstants.FONT_NORMAL);
        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        add(lblUser, gbc);

        txtUser = new JTextField(20);
        txtUser.setText("admin");
        gbc.gridx = 1;
        add(txtUser, gbc);

        JLabel lblPass = new JLabel("Mật khẩu:");
        lblPass.setFont(AppConstants.FONT_NORMAL);
        gbc.gridx = 0; gbc.gridy = 2;
        add(lblPass, gbc);

        txtPass = new JPasswordField(20);
        txtPass.setText("admin123");
        gbc.gridx = 1;
        add(txtPass, gbc);

        JButton btnLogin = new JButton("Đăng Nhập");
        btnLogin.setBackground(AppConstants.PRIMARY);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(AppConstants.FONT_TITLE);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(btnLogin, gbc);

        btnLogin.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword());

        UserService service = new UserService();
        if (service.login(user, pass)) {
            DialogUtils.success("Đăng nhập thành công!");
            dispose();
            new MainFrame(); // Mở màn hình chính
        } else {
            DialogUtils.error("Sai tên đăng nhập hoặc mật khẩu!");
        }
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
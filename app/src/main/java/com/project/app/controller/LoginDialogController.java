package com.project.app.controller;

import com.project.app.di.activej.ActiveJ;
import com.project.domain.user.interactor.SignUp;
import com.project.domain.user.model.UserResponseModel;
import com.project.domain.user.presenter.SignUpPresenter;
import com.project.domain.view.View;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author dhelarius 12/4/2022
 * periodent
 */
public class LoginDialogController implements View<UserResponseModel> {

    private LoginSuccessListener listener;

    public interface LoginSuccessListener {
        void onLoginSuccess();
    }

    public void setLoginSuccessListener(LoginSuccessListener listener) {
        this.listener = listener;
    }

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private Stage dialogStage;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {}

    /**
     * Establecer escenario para este dialog
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    public void handleSignUp() {
        var signUp = ActiveJ.getInstance(SignUp.class);
        var signUpPresenter = new SignUpPresenter(signUp, this);
        signUpPresenter.signUp(usernameField.getText(), passwordField.getText());
    }

    /**
     * Cerra cuando el usuario presiona cancelar
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    @FXML
    @Override
    public void show(UserResponseModel value) {
        System.out.println(value.toString());
        listener.onLoginSuccess();
        dialogStage.close();
    }

    @Override
    public void showErrorMessage(Throwable throwable) {
        // Mostrar mensage de error.
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Datos Inválidos");
        alert.setHeaderText(throwable.getMessage());
        alert.setContentText("Error al iniciar sesión");
        alert.showAndWait();
    }
}

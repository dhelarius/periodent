package com.project.app.user.view;

import com.project.app.user.model.permission.FXPermission;
import com.project.app.user.viewmodel.PermissionViewModel;
import com.project.app.util.DialogUtil;
import com.project.app.util.StageEventUtil;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author dhelarius 30/4/2022
 * periodent
 */
public class EditPermissionView implements FxmlView<PermissionViewModel>, Initializable {

    @InjectViewModel
    private PermissionViewModel viewModel;

    private Stage stage;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField keyField;

    public void setStage(Stage stage) {
        this.stage = stage;
        handleStageEvents(stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var permission = viewModel.getPermission();
        descriptionField.textProperty().bindBidirectional(permission.descriptionProperty());
        keyField.textProperty().bindBidirectional(permission.keyProperty());
    }

    private void handleStageEvents(Stage stage) {
        StageEventUtil.setOnCloseRequest(this::onCancel, stage);
    }

    private int onCancel() {
        viewModel.getPermissions().removeAll(viewModel.getPermissions());
        viewModel.loadPermissions();
        return 0;
    }

    @FXML
    public void handleSave() {
        if(viewModel.isNewPermission()) {
            viewModel.addPermission((response, throwable) -> {
                if(throwable != null) {
                    DialogUtil.errorMessage(throwable, "Nuevo Permiso",
                            "Error al guardar el permiso");
                    return;
                }

                viewModel.getPermissions().add(new FXPermission(response.getId(),
                        response.getDescription(), response.getKey(), response.isActive()));

                descriptionField.clear();
                keyField.clear();

                DialogUtil.message("Exito", "¡Se ha creado el permiso satisfactoriamente!");
            });
        } else {
            viewModel.updatePermission((response, throwable) -> {
                if(throwable != null) {
                    DialogUtil.errorMessage(throwable, "Editar Permiso",
                            "Error al actualizar permiso");
                    return;
                }

                DialogUtil.message("Exito", "¡Se ha actualizado el permiso satisfactoriamente!");
                stage.close();
            });
        }
    }

    @FXML
    public void handleCancel() {
        stage.close();
        onCancel();
    }
}

package godomicilios.mdc.restaurantego.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import cn.refactor.lib.colordialog.PromptDialog;
import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import godomicilios.mdc.restaurantego.R;

/**
 * Creado por Deimer el 22/08/17.
 */

public class MaterialDialog {

    //Variable para alerta asincronica
    private AlertDialog progressDialog;
    private Context context;

    private void dialogs(){}

    public MaterialDialog(Context context){
        this.context = context;
        dialogs();
    }

    public void setContext(Context context){
        this.context = context;
    }

    //Funcion que lanza una alerta asincronica para los procesos que requieren tiempo
    public void dialogProgress(String title){
        progressDialog = new SpotsDialog(context, title, R.style.CustomDialog);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    //Funcion que permite cancelar una alerta asincronica
    public void cancelProgress(){
        progressDialog.cancel();
    }

    public void dialogWarnings(String title, String message){
        new PromptDialog(context).setDialogType(PromptDialog.DIALOG_TYPE_WARNING)
                .setTitleText(title)
                .setContentText(message)
                .setAnimationEnable(true)
                .setPositiveListener("Ok", new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void toastInfo(String message){
        Toasty.info(context, message, Toast.LENGTH_LONG, true).show();
    }

    public void toastSuccess(String message){
        Toasty.success(context, message, Toast.LENGTH_LONG, true).show();
    }

    public void toastWarning(String message) {
        Toasty.warning(context, message, Toast.LENGTH_LONG, true).show();
    }

    public void toastError(String message){
        Toasty.error(context, message, Toast.LENGTH_LONG, true).show();
    }

}

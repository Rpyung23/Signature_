package com.uth.e24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.kyanogen.signatureview.SignatureView;
import com.uth.e24.database.DBSIGNATURE;
import com.uth.e24.models.cSignature;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;


public class NewSignature extends AppCompatActivity
{

    private DBSIGNATURE oDbsignature;

    private TextInputEditText oTextInputEditTextDescripcion;
    private MaterialButton oMaterialButtonSalvar;

    private SignatureView oSignatureView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_signature);

        this.oTextInputEditTextDescripcion = this.findViewById(R.id.txtDescripcion);
        this.oMaterialButtonSalvar = this.findViewById(R.id.btn_salvar);
        this.oSignatureView = this.findViewById(R.id.signature_view);
        oDbsignature = new DBSIGNATURE(NewSignature.this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        this.oMaterialButtonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               if(!oSignatureView.isBitmapEmpty() && !oTextInputEditTextDescripcion.getText().toString().isEmpty()){
                   cSignature oS = new cSignature();
                   oS.setDescripcion(oTextInputEditTextDescripcion.getText().toString());
                   oS.setFirma_digital(oSignatureView.getSignatureBitmap());
                   if(oDbsignature.createSignature(oS)){
                       Toast.makeText(NewSignature.this, "FIRMA DIJITAL GUARDA CON EXITO", Toast.LENGTH_SHORT).show();
                       oSignatureView.clearCanvas();
                       oTextInputEditTextDescripcion.setText("");
                   }else{
                       Toast.makeText(NewSignature.this, "ERROR AL GUARDAR LA FIRMA", Toast.LENGTH_SHORT).show();
                   }
               }else {
                   Toast.makeText(NewSignature.this, "EXISTEN DATOS VACIOS", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
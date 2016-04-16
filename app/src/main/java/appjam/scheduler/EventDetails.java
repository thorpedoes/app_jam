package appjam.scheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EventDetails extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        registerButtons();
    }

    private void registerButtons() {
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        if(cancelButton != null)
            cancelButton.setOnClickListener(getExitListener());
        if(saveButton != null)
            saveButton.setOnClickListener(getSaveListener());
    }

    private View.OnClickListener getExitListener() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
               finish();
            }
        };
    }

    private View.OnClickListener getSaveListener() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                Intent resultData = new Intent();
                setResult(RESULT_OK, resultData);
                finish();
            }
        };
    }
}

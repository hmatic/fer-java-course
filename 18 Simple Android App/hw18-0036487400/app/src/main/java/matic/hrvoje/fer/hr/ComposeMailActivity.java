package matic.hrvoje.fer.hr;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Activity which provides sending emails.
 * Activity takes recipient, mail title and message and forwards it to email application.
 * Emails have CC equal to static list of predefined emails.
 */
public class ComposeMailActivity extends AppCompatActivity {
    /**
     * Regex for checking valid email addresses.
     */
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final String CC_LIST[] = { "ana@baotic.org","marcupic@gmail.com "};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_mail);

        Button btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText inputMessage = findViewById(R.id.input_message);
                EditText inputTitle = findViewById(R.id.input_title);
                EditText inputMail = findViewById(R.id.input_send_to);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { inputMail.getText().toString() });
                intent.putExtra(android.content.Intent.EXTRA_CC, CC_LIST);
                intent.putExtra(Intent.EXTRA_SUBJECT, inputTitle.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT, inputMessage.getText().toString());

                if(verifyData(inputMessage, inputTitle, inputMail)) {
                    startActivity(intent);
                    finish();
                } else {
                    TextView status = findViewById(R.id.status_label);
                    status.setText(R.string.error_msg);
                }
            }
        });
    }

    /**
     * Method which validates all input data in ComposeMail activity.
     * No fields can be empty and email must be valid.
     * @param inputMessage input message
     * @param inputTitle input title
     * @param inputMail input email
     * @return true if data is valid, false otherwise
     */
    public boolean verifyData(EditText inputMessage, EditText inputTitle, EditText inputMail) {
        if(inputMessage.getText().toString().isEmpty() ||
                inputTitle.getText().toString().isEmpty() ||
                inputMail.getText().toString().isEmpty()) {
            return false;
        }
        if(!validateMailAdress(inputMail.getText().toString())) return false;
        return true;
    }

    /**
     * Method which validates is string valid email.
     * @param emailStr email
     * @return true if mail is valid, false otherwise
     */
    public static boolean validateMailAdress(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}

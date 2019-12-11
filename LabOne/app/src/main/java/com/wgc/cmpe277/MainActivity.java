package com.wgc.cmpe277;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    MyCalculator myCalculator;
    TextView text;
    boolean enterTextDone = true;
    boolean textUpdated = false;
    Button[] numButtons;
    Button[] operationButtons;
    Button clear;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myCalculator = new MyCalculator();
        initialButtons();
    }

    private double readValFromText() {
        double val = 0;
        boolean isNegative = false;
        CharSequence charSequence = text.getText();
        i = 0;
        double decimalScale = 10;
        boolean decimalPart = false;
        while(i < charSequence.length()) {
            char c = charSequence.charAt(i++);
            if(c == '-') {
                isNegative = true;
            }else if(c == '.') {
                decimalPart = true;
            }else if(!decimalPart) {
                val = val*10 + c - '0';
            }else {
                val += (c - '0')/decimalScale;
                decimalScale *= 10;
            }
        }
        if(isNegative) {
            val = -val;
        }
        System.out.println(val);
        return val;
    }

    private void initialButtons() {
        text = findViewById(R.id.text);
        clear = findViewById(R.id.clear);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCalculator.clear();
                text.setText("0");
            }
        });


        numButtons = new Button[11];
        i = 0;
        numButtons[i++] = findViewById(R.id.num_0);
        numButtons[i++] = findViewById(R.id.num_1);
        numButtons[i++] = findViewById(R.id.num_2);
        numButtons[i++] = findViewById(R.id.num_3);
        numButtons[i++] = findViewById(R.id.num_4);
        numButtons[i++] = findViewById(R.id.num_5);
        numButtons[i++] = findViewById(R.id.num_6);
        numButtons[i++] = findViewById(R.id.num_7);
        numButtons[i++] = findViewById(R.id.num_8);
        numButtons[i++] = findViewById(R.id.num_9);
        numButtons[i++] = findViewById(R.id.dot);  // decimal point

        numButtons[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(enterTextDone) {
                        enterTextDone = false;
                        text.setText(null);
                    }
                    String t = text.getText().toString();
                    textUpdated = true;
                    if(t.equals("0")) {
                        return;
                    }else {
                        text.setText(t + "0");
                    }
                }
        });

        numButtons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enterTextDone) {
                    enterTextDone = false;
                    text.setText(null);
                }
                textUpdated = true;
                String t = text.getText().toString() + 1;
                text.setText(t);
            }
        });

        numButtons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enterTextDone) {
                    enterTextDone = false;
                    text.setText(null);
                }
                textUpdated = true;
                String t = text.getText().toString() + 2;
                text.setText(t);
            }
        });

        numButtons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enterTextDone) {
                    enterTextDone = false;
                    text.setText(null);
                }
                textUpdated = true;
                String t = text.getText().toString() + 3;
                text.setText(t);
            }
        });

        numButtons[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enterTextDone) {
                    enterTextDone = false;
                    text.setText(null);
                }
                textUpdated = true;
                String t = text.getText().toString() + 4;
                text.setText(t);
            }
        });

        numButtons[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enterTextDone) {
                    enterTextDone = false;
                    text.setText(null);
                }
                textUpdated = true;
                String t = text.getText().toString() + 5;
                text.setText(t);
            }
        });


        numButtons[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enterTextDone) {
                    enterTextDone = false;
                    text.setText(null);
                }
                textUpdated = true;
                String t = text.getText().toString() + 6;
                text.setText(t);
            }
        });

        numButtons[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enterTextDone) {
                    enterTextDone = false;
                    text.setText(null);
                }
                textUpdated = true;
                String t = text.getText().toString() + 7;
                text.setText(t);
            }
        });

        numButtons[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enterTextDone) {
                    enterTextDone = false;
                    text.setText(null);
                }
                textUpdated = true;
                String t = text.getText().toString() + 8;
                text.setText(t);
            }
        });

        numButtons[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enterTextDone) {
                    enterTextDone = false;
                    text.setText(null);
                }
                textUpdated = true;
                String t = text.getText().toString() + 9;
                text.setText(t);
            }
        });

        numButtons[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enterTextDone) {
                    enterTextDone = false;
                    text.setText(null);
                }
                textUpdated = true;
                String t = text.getText().toString();
                if(t.length() == 0) {
                    text.setText("0.");
                }else if(t.charAt(t.length()-1) != '.') {
                    text.setText(t + ".");
                }
            }
        });


        //  operators   + - * / =
        operationButtons = new Button[5];
        i = 0;
        operationButtons[i++] = findViewById(R.id.plus);
        operationButtons[i++] = findViewById(R.id.minus);
        operationButtons[i++] = findViewById(R.id.multiply);
        operationButtons[i++] = findViewById(R.id.div);
        operationButtons[i++] = findViewById(R.id.cal);

        operationButtons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterTextDone = true;
                if(textUpdated) {
                    Double res = myCalculator.input( readValFromText(),'+');
                    if(res != null) {
                        text.setText( String.valueOf(res));
                    }
                    textUpdated = false;
                }
            }
        });

        operationButtons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterTextDone = true;
                if(textUpdated) {
                    Double res = myCalculator.input( readValFromText() , '-');
                    if(res != null) {
                        text.setText( String.valueOf(res));
                    }
                }
            }
        });

        operationButtons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterTextDone = true;
                if (textUpdated) {
                    Double res = myCalculator.input(readValFromText(), '*');
                    if (res != null) {
                        text.setText(String.valueOf(res));
                    }
                }
            }
        });

        operationButtons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterTextDone = true;
                if (textUpdated) {
                    Double res = myCalculator.input(readValFromText(), '/');
                    if (res != null) {
                        text.setText(String.valueOf(res));
                    }
                }
            }
        });

        operationButtons[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterTextDone = true;
                Double res = myCalculator.input( readValFromText(),'=');
                if(res != null) {
                    text.setText( String.valueOf(res));
                }
            }
        });
    }
}



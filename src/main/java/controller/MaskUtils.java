package controller;

import javafx.scene.control.TextField;

public class MaskUtils {

    // 000.000.000-00
    public static void aplicarMascaraCpf(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            String digits = newValue.replaceAll("[^\\d]", "");
            if (digits.length() > 11) digits = digits.substring(0, 11);

            StringBuilder formatted = new StringBuilder();
            for (int i = 0; i < digits.length(); i++) {
                if (i == 3 || i == 6) {
                    formatted.append(".");
                } else if (i == 9) {
                    formatted.append("-");
                }
                formatted.append(digits.charAt(i));
            }

            if (!textField.getText().equals(formatted.toString())) {
                textField.setText(formatted.toString());
                textField.positionCaret(formatted.length());
            }
        });
    }

    // (00) 00000-0000
    public static void aplicarMascaraTelefone(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            String digits = newValue.replaceAll("[^\\d]", "");
            if (digits.length() > 11) digits = digits.substring(0, 11);

            StringBuilder formatted = new StringBuilder();
            for (int i = 0; i < digits.length(); i++) {
                if (i == 0) {
                    formatted.append("(");
                } else if (i == 2) {
                    formatted.append(") ");
                } else if (i == 7) {
                    formatted.append("-");
                }
                formatted.append(digits.charAt(i));
            }

            if (!textField.getText().equals(formatted.toString())) {
                textField.setText(formatted.toString());
                textField.positionCaret(formatted.length());
            }
        });
    }

    //ABC-1D23
    public static void aplicarMascaraPlaca(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            String upper = newValue.toUpperCase().replaceAll("[^A-Z0-9]", "");
            if (upper.length() > 7) upper = upper.substring(0, 7);

            StringBuilder formatted = new StringBuilder();
            for ( int i = 0; i < upper.length(); i++){
                char c = upper.charAt(i);
                if ( i < 3 || i == 4){
                    if ( c >= '0' && c <= '9') continue;
                }else{
                    if (c>= 'A' && c <= 'Z') continue;
                }

                formatted.append(c);
            }

            if (formatted.length() > 3) {
                formatted.insert(3, "-");
            }
            if (!textField.getText().contentEquals(formatted)) {
                textField.setText(formatted.toString());
                textField.positionCaret(formatted.length());
            }
        });
    }

// 0000
    public static void aplicarMascaraAno(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            String digits = newValue.replaceAll("[^\\d]", "");
            if (digits.length() > 4) digits = digits.substring(0, 4);
            if (!textField.getText().equals(digits)) {
                textField.setText(digits);
                textField.positionCaret(digits.length());
            }
        });
    }

    //DD/MM/AAAA
    public static void aplicarMascaraData(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }

            String digits = newValue.replaceAll("\\D", "");

            if (digits.length() > 8) {
                digits = digits.substring(0, 8);
            }

            StringBuilder formatted = new StringBuilder();

            for (int i = 0; i < digits.length(); i++) {
                if (i == 2 || i == 4) {
                    formatted.append("/");
                }
                formatted.append(digits.charAt(i));
            }

            if (!formatted.toString().equals(textField.getText())) {
                textField.setText(formatted.toString());
                textField.positionCaret(formatted.length());
            }
        });
    }

    public static void aplicarMascaraCNH(TextField textField){
       textField.textProperty().addListener(((observable, oldValue, newValue) -> {
        if ( newValue == null) return;

        String digits = newValue.replaceAll("\\D","");
        if (digits.length() > 11){
            digits = digits.substring(0,11);
        }
        if (!digits.equals(textField.getText())){
            textField.setText(digits);
            textField.positionCaret(digits.length());
        }


       }));
    }
    public static void aplicarMascaraPreco(TextField textField){
        textField.setText("R$ 0,00");

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;

            String textoAtual = textField.getText();

            String digits = newValue.replaceAll("\\D", "");

            if (digits.isEmpty()) {
                digits = "0";
            }

            long valorCentavos = Long.parseLong(digits);

            String valorFormatado = String.format("R$ %,.2f", valorCentavos / 100.0)
                    .replace(".", "#")
                    .replace(",", ".")
                    .replace("#", ",");

            if (!textoAtual.equals(valorFormatado)) {
                textField.setText(valorFormatado);
                textField.positionCaret(valorFormatado.length());
            }


        });
    }
}
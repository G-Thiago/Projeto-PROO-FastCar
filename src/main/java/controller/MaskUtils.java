package controller;

import javafx.scene.control.TextField;

public class MaskUtils {

    /**
     * Aplica máscara de CPF: 000.000.000-00 (com barras/traços conforme digita)
     */
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

    /**
     * Aplica máscara de Telefone: (00) 00000-0000
     */
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

    /**
     * Aplica máscara de Placa: ABC-1234
     */
    public static void aplicarMascaraPlaca(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            String upper = newValue.toUpperCase().replaceAll("[^A-Z0-9]", "");
            if (upper.length() > 7) upper = upper.substring(0, 7);

            StringBuilder formatted = new StringBuilder(upper);
            if (upper.length() > 3) {
                formatted.insert(3, "-");
            }
            if (!textField.getText().equals(formatted.toString())) {
                textField.setText(formatted.toString());
                textField.positionCaret(formatted.length());
            }
        });
    }

    /**
     * Limita o campo a apenas 4 dígitos numéricos para Ano
     */
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
    public static void aplicarMascaraData(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }

            // Remove tudo o que não for dígito
            String digits = newValue.replaceAll("\\D", "");

            // Limita o tamanho máximo a 8 dígitos (DDMMAAAA)
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

            // Evita loop infinito atualizando apenas se o texto formatado for diferente
            if (!formatted.toString().equals(textField.getText())) {
                textField.setText(formatted.toString());
                // Posiciona o cursor sempre no final do texto digitado
                textField.positionCaret(formatted.length());
            }
        });
    }
}
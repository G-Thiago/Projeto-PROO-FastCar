package controller;

import javafx.scene.control.TextField;

public class MaskUtils {

    // Aplica máscara de CPF: 000.000.000-00

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

    // Aplica máscara de Telefone: (00) 00000-0000
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

    //  máscara de Placa: ABC-1D23

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
                // Posiciona o cursor sempre no final do texto digitado
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

            // Evita loop infinito caso o texto já esteja formatado exatamente igual
            String textoAtual = textField.getText();

            // 1. Remove tudo o que não for número
            String digits = newValue.replaceAll("\\D", "");

            // Se apagar tudo, reseta para R$ 0,00
            if (digits.isEmpty()) {
                digits = "0";
            }

            // Remove zeros à esquerda desnecessários (ex: "0015" vira "15")
            // Mas mantém se for apenas "0"
            long valorCentavos = Long.parseLong(digits);

            // 2. Formata dividindo por 100 para transformar centavos em reais (ex: 15000 vira 150,00)
            String valorFormatado = String.format("R$ %,.2f", valorCentavos / 100.0)
                    .replace(".", "#") // Troca temporária para ajustar o padrão brasileiro
                    .replace(",", ".")
                    .replace("#", ",");

            // 3. Atualiza o campo apenas se houver diferença para evitar loop no listener
            if (!textoAtual.equals(valorFormatado)) {
                textField.setText(valorFormatado);
                textField.positionCaret(valorFormatado.length()); // Mantém o cursor sempre no final
            }


        });
    }
}
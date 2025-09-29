package mateus.madeira.desafiopicpay.controller.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class CpfCnpjValidator implements ConstraintValidator<CpfCnpj, String> {

    // Regex para remover caracteres não numéricos
    private static final Pattern NON_NUMERIC = Pattern.compile("[^\\d]");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Nulo ou vazio é tratado pelo @NotBlank, aqui consideramos válido
        if (value == null || value.isBlank()) {
            return true;
        }

        // Remove pontuação
        String cleanedValue = NON_NUMERIC.matcher(value).replaceAll("");

        // Verifica o tamanho e chama a validação correspondente
        if (cleanedValue.length() == 11) {
            return isCpfValido(cleanedValue);
        } else if (cleanedValue.length() == 14) {
            return isCnpjValido(cleanedValue);
        }

        return false;
    }

    private boolean isCpfValido(String cpf) {

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int[] peso = {10, 9, 8, 7, 6, 5, 4, 3, 2};
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Integer.parseInt(String.valueOf(cpf.charAt(i))) * peso[i];
            }
            int resto = soma % 11;
            int dv1 = (resto < 2) ? 0 : 11 - resto;

            if (dv1 != Integer.parseInt(String.valueOf(cpf.charAt(9)))) {
                return false;
            }

            soma = 0;
            int[] peso2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
            for (int i = 0; i < 10; i++) {
                soma += Integer.parseInt(String.valueOf(cpf.charAt(i))) * peso2[i];
            }
            resto = soma % 11;
            int dv2 = (resto < 2) ? 0 : 11 - resto;

            return dv2 == Integer.parseInt(String.valueOf(cpf.charAt(10)));
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isCnpjValido(String cnpj) {
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        try {
            int[] peso = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int soma = 0;
            for (int i = 0; i < 12; i++) {
                soma += Integer.parseInt(String.valueOf(cnpj.charAt(i))) * peso[i];
            }
            int resto = soma % 11;
            int dv1 = (resto < 2) ? 0 : 11 - resto;

            if (dv1 != Integer.parseInt(String.valueOf(cnpj.charAt(12)))) {
                return false;
            }

            soma = 0;
            int[] peso2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            for (int i = 0; i < 13; i++) {
                soma += Integer.parseInt(String.valueOf(cnpj.charAt(i))) * peso2[i];
            }
            resto = soma % 11;
            int dv2 = (resto < 2) ? 0 : 11 - resto;

            return dv2 == Integer.parseInt(String.valueOf(cnpj.charAt(13)));
        } catch (Exception e) {
            return false;
        }
    }
}
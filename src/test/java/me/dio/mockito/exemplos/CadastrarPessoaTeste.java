package me.dio.mockito.exemplos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class CadastrarPessoaTeste {

    @Mock
    private ApiDosCorreios apiDosCorreios;

    @InjectMocks
    private CadastrarPessoa cadastrarPessoa;

    @Test
    void cadastrarPessoa(){
        DadosLocalizacao dadosLocalizacao = new DadosLocalizacao("SP", "São Paulo", "Rua Jupi","apto","Santo Amaro");
        Mockito.when(apiDosCorreios.buscaDadosComBaseNoCep(anyString())).thenReturn(dadosLocalizacao);
        Pessoa pessoa = cadastrarPessoa.cadastrarPessoa("Cláudio","3333333", LocalDate.now(),"04755050");
        DadosLocalizacao enderecoPessoa = pessoa.getEndereco();
        assertEquals(dadosLocalizacao.getBairro(),enderecoPessoa.getBairro());
        assertEquals(dadosLocalizacao.getCidade(),enderecoPessoa.getCidade());
        assertEquals(dadosLocalizacao.getUf(),enderecoPessoa.getUf());
    }

    @Test
    void lancarExceptionQuandoChamarApidosCorreios(){
        Mockito.when(apiDosCorreios.buscaDadosComBaseNoCep(anyString())).thenThrow(IllegalArgumentException.class);
        Assertions.assertThrows(IllegalArgumentException.class,()-> cadastrarPessoa.cadastrarPessoa("Cláudio","3333333", LocalDate.now(),"04755050"));
    }
}

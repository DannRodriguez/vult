package mx.ine.sustseycae.dto;

import mx.ine.sustseycae.dto.vo.VOCombo;
import mx.ine.sustseycae.dto.vo.VOComboPendientes;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DTOListaSustituido {

    private List<VOComboPendientes> pendientesPorCubrir;
    private List<VOCombo> supervisor;
    private List<VOCombo> capacitador;

}

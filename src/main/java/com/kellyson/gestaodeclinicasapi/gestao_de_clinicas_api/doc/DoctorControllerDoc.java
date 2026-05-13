package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.doc;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.DoctorFiltersRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.DoctorRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.DoctorResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(
        name = "Doctors",
        description = "Operações para cadastro, listagem e desativação de médicos"
)
public interface DoctorControllerDoc {

    @Operation(summary = "Cadastrar médico", description = "Cadastra um Médico no sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Médico cadastrado com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro ao cadastrar Médico, Dados invalidos na requisição",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<DoctorResponseDTO> createDoctor(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Usuario manda os dados do Médico a ser criado",
                    required = true)
            @Valid @RequestBody DoctorRequestDTO doctorRequestDTO);


    @Operation(summary = "Listar Médicos")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listagem  efetuada"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Filtros invalidos, verifique os parametros informados",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<Page<DoctorResponseDTO>> listDoctors(
            @ModelAttribute DoctorFiltersRequestDTO filtersRequestDTO,

            Pageable pageable);


    @Operation(summary = "Desativar Médico")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Médico desativado com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Médico não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "Médico ja está desativado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<Void> deactiveDoctor(
            @Parameter(description = "Usuario manda o ID do Médico no path da requisição", required = true)

            @PathVariable Long doctorId);
}


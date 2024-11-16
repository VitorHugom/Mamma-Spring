package com.example.mamma_erp.services;

import com.example.mamma_erp.entities.fornecedores.FornecedoresRepository;
import com.example.mamma_erp.entities.itens_recebimento_mercadorias.ItensRecebimentoMercadorias;
import com.example.mamma_erp.entities.produtos.ProdutosRepository;
import com.example.mamma_erp.entities.recebimento_mercadorias.*;
import com.example.mamma_erp.entities.tipos_cobranca.TiposCobrancaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecebimentoMercadoriasService {

    @Autowired
    private RecebimentoMercadoriasRepository recebimentoMercadoriasRepository;

    @Autowired
    private FornecedoresRepository fornecedoresRepository;

    @Autowired
    private TiposCobrancaRepository tiposCobrancaRepository;

    @Autowired
    private ProdutosRepository produtosRepository;

    public List<RecebimentoMercadoriasResponseDTO> listarTodos(){
        return recebimentoMercadoriasRepository.findAll().stream().map(RecebimentoMercadoriasResponseDTO::new).collect(Collectors.toList());
    }

    public Optional<RecebimentoMercadorias> buscarPorId(Integer id) {
        return recebimentoMercadoriasRepository.findById(id);
    }

    public Optional<RecebimentoMercadoriasBuscaResponseDTO> simplesBuscaPorId(Integer id) {
        return recebimentoMercadoriasRepository.findById(id)
                .map(RecebimentoMercadoriasBuscaResponseDTO::new);
    }

    public RecebimentoMercadorias criarRecebimento(RecebimentoMercadoriasRequestDTO dto) {
        RecebimentoMercadorias recebimento = new RecebimentoMercadorias();
        recebimento.setFornecedor(fornecedoresRepository.findById(dto.idFornecedor()).orElseThrow());
        recebimento.setTipoCobranca(tiposCobrancaRepository.findById(dto.idTipoCobranca()).orElseThrow());
        recebimento.setDataRecebimento(dto.dataRecebimento());

        dto.itens().forEach(itemDto -> {
            ItensRecebimentoMercadorias item = new ItensRecebimentoMercadorias();
            item.setProduto(produtosRepository.findById(itemDto.idProduto()).orElseThrow());
            item.setQuantidade(itemDto.quantidade());
            item.setValorUnitario(itemDto.valorUnitario());
            item.setRecebimento(recebimento);
            recebimento.getItens().add(item);
        });

        return recebimentoMercadoriasRepository.save(recebimento);
    }

    public Optional<RecebimentoMercadorias> atualizarRecebimento(Integer id, RecebimentoMercadoriasRequestDTO dto) {
        return recebimentoMercadoriasRepository.findById(id).map(recebimento -> {
            // Atualizar dados do recebimento
            recebimento.setFornecedor(fornecedoresRepository.findById(dto.idFornecedor()).orElseThrow());
            recebimento.setTipoCobranca(tiposCobrancaRepository.findById(dto.idTipoCobranca()).orElseThrow());
            recebimento.setDataRecebimento(dto.dataRecebimento());

            // Limpa a lista de itens atual, permitindo que o orphanRemoval remova itens não mais associados
            recebimento.getItens().clear();

            // Recria a lista de itens com os dados do DTO
            dto.itens().forEach(itemDto -> {
                ItensRecebimentoMercadorias item = new ItensRecebimentoMercadorias();
                item.setProduto(produtosRepository.findById(itemDto.idProduto()).orElseThrow());
                item.setQuantidade(itemDto.quantidade());
                item.setValorUnitario(itemDto.valorUnitario());
                item.setRecebimento(recebimento);  // Define o recebimento pai
                recebimento.getItens().add(item);
            });

            // Salva o recebimento com a nova lista de itens
            return recebimentoMercadoriasRepository.save(recebimento);
        });
    }

    public boolean deletarRecebimento(Integer id) {
        if (recebimentoMercadoriasRepository.existsById(id)) {
            recebimentoMercadoriasRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Page<RecebimentoMercadoriasBuscaResponseDTO> buscarRecebimentos(Pageable pageable) {
        return recebimentoMercadoriasRepository.findAllOrderedByDataRecebimento(pageable);
    }

    public Page<RecebimentoMercadoriasBuscaResponseDTO> buscarRecebimentosPorRazaoSocial(String razaoSocial, Pageable pageable) {
        return recebimentoMercadoriasRepository.findRecebimentosByFornecedorRazaoSocial(razaoSocial + "%", pageable);
    }
}

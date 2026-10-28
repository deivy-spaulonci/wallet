package com.br.wallet.dto.ref;

import java.math.BigInteger;
import java.util.UUID;

public record PessoaRefDTO(
        BigInteger id,
        UUID uuid
){}

package com.paulohva.pokertools.dto;

import com.paulohva.pokertools.services.exception.InvalidRequestException;
import com.paulohva.pokertools.utils.PokerGameUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class CardDTO implements Serializable {
    @NotNull
    private String value;
    @NotNull
    private Character kind;

    public CardDTO() {
    }

    public CardDTO(String value, char kind) {
        this.value = value;
        this.kind = kind;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Character getKind() {
        return kind;
    }

    public void setKind(Character kind) {
        this.kind = kind;
    }

    public int getRank() {
        Optional<Integer> rankList;
        try {
            rankList = PokerGameUtils.CARD_VALUE_TO_RANK.entrySet()
                    .stream()
                    .filter(i -> i.getKey().equals(value))
                    .map(m -> m.getValue()).findFirst();
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidRequestException(e.getMessage());
        }
        return rankList.orElseThrow(() -> new InvalidRequestException(String.format("Card value not exists: %s", toString())));
    }

    public boolean isCardValid() {
        boolean isKindValid = PokerGameUtils.CARD_KIND_SET.stream().anyMatch(i -> i == this.kind);
        if (getRank() == 0 || !isKindValid) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardDTO cardDTO = (CardDTO) o;
        return Objects.equals(value, cardDTO.value) &&
                Objects.equals(kind, cardDTO.kind);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, kind);
    }

    @Override
    public String toString() {
        return "CardDTO{" +
                "value='" + value + '\'' +
                ", kind=" + kind +
                '}';
    }
}

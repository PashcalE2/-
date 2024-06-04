package main.blps_lab2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab2.exception.NotEnoughMoneyOnCardException;
import main.blps_lab2.repository.BankRepository;
import main.blps_lab2.service.interfaces.BankServiceInterface;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BankService implements BankServiceInterface {
    private final BankRepository bankRepository;

    @Override
    public void removeMoney(Long cardId, Integer money) throws NotEnoughMoneyOnCardException {
        try {
            bankRepository.removeMoney(cardId, money);
        }
        catch (RuntimeException e) {
            throw new NotEnoughMoneyOnCardException(cardId, money);
        }

        log.info(String.format("Операция списания денег выполнена:\n%d\n%d\n", cardId, money));
    }
}

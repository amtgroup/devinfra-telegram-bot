package amtgroup.devinfra.telegram.components.telegram.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Vitaly Ogoltsov
 */
@Service
public class IntegramCommandService {

    private final IntegramCommandRepository repository;


    @Autowired
    public IntegramCommandService(IntegramCommandRepository repository) {
        this.repository = repository;
    }

}

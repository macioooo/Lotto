package org.maciooo.domain.numbergenerator;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
interface NumberGeneratorRepository extends MongoRepository<WinningNumbers, String> {

    WinningNumbers findWinningNumbersByDrawDate(String drawDate);
}

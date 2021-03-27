package com;

import lombok.extern.slf4j.Slf4j;
import org.jooq.lambda.Unchecked;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.io.FileUtils;

import javax.xml.bind.DatatypeConverter;

@Slf4j
@Service
public class ExpensiveOps {



    private static final BigDecimal TWO = new BigDecimal("2");

    @Cacheable("primes")
    public Boolean isPrime(int n) {
        log.debug("Computing isPrime({})", n);


        BigDecimal number = new BigDecimal(n);
        if (number.compareTo(TWO)<=0) {
            return true;
        }
        if (number.remainder(TWO).equals(BigDecimal.ZERO)) {
            return false;
        }
        for (BigDecimal divisor = new BigDecimal("3");
            divisor.compareTo(number.divide(TWO)) < 0;
            divisor = divisor.add(TWO)) {
            if (number.remainder(divisor).equals(BigDecimal.ZERO)) {
                return false;
            }
        }
        return true;

    }


    @Cacheable("folders")
    public String hashAllFiles(File folder) throws IOException, NoSuchAlgorithmException {

        log.debug("Computing hashAllFiles({})", folder);
        MessageDigest md = MessageDigest.getInstance("MD5");
        for (int i=0; i<2; i++) {
            Files.walk(folder.toPath())
                 .map(Path::toFile)
                .filter(File::isFile)
                .map(Unchecked.function(FileUtils::readFileToString))
                .forEach(s -> md.update(s.getBytes()));
        }
        byte[] digest =  md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();

    }

    public void invalidateCacheForFolder(File file) {
    }
}

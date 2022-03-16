package pl.futurecollars.invoicing.db.file

import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path

class IdServiceTest extends Specification {

    private Path path = File.createTempFile('idfile', '.txt').toPath()

    def 'next id should be 1 when new id service is initiated' (){
        given:
        IdService idService = new IdService(path, new FileService())
        expect:
        ['1'] == Files.readAllLines(path)
        and:
        1 == idService.nextIdAndIncrement
        ['2'] == Files.readAllLines(path)
        and:
        2 == idService.nextIdAndIncrement
        ['3'] == Files.readAllLines(path)
        and:
        3 == idService.nextIdAndIncrement
        ['4'] == Files.readAllLines(path)
    }

    def 'when there already is a number in the file next Id should start with it' () {
        given:
        Files.writeString(path, "10")
        IdService idService = new IdService(path, new FileService())
        expect:
        ['10'] == Files.readAllLines(path)
        and:
        10 == idService.nextIdAndIncrement
        ['11'] == Files.readAllLines(path)
    }


}

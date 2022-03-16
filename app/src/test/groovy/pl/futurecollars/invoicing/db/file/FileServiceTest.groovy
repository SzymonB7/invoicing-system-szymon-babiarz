package pl.futurecollars.invoicing.db.file

import spock.lang.Specification

import java.nio.file.Path

class FileServiceTest extends Specification {
    private FileService fileService = new FileService()
    private Path path = File.createTempFile('lines', '.txt').toPath()

    def 'should append line to file'() {
        given:
        String line = 'Test test test'
        expect:
        [] == fileService.readAllLines(path)
        when:
        fileService.appendLineToFile(path, line)
        then:
        [line] == fileService.readAllLines(path)
        when:
        fileService.appendLineToFile(path, line)
        then:
        [line, line] == fileService.readAllLines(path)

    }

    def 'should write line to file and overwrite it with another line'() {
        given:
        String line1 = 'First test line'
        String line2 = 'Second test line'
        when:
        fileService.writeToFile(path, line1)
        then:
        [line1] == fileService.readAllLines(path)
        when:
        fileService.writeToFile(path, line2)
        then:
        [line2] == fileService.readAllLines(path)

    }

    def 'should overwrite file with given list'() {
        given:
        String line1 = 'First test line'
        String line2 = 'Second test line'
        String line3 = 'Third test line'
        def list = [line1, line2]
        when:
        fileService.appendLineToFile(path, line3)
        fileService.appendLineToFile(path, line1)
        then:
        [line3, line1] == fileService.readAllLines(path)
        when:
        fileService.overwriteLinesInFile(path, list)
        then:
        [line1, line2] == fileService.readAllLines(path)

    }

}

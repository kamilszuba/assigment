import log.domain.Facade;
import log.domain.FacadeConfig;
import log.domain.FilePath;
import log.infrastructure.HsqlDbEventRepositoryAdapter;

import javax.validation.Validation;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("Program started.");

        Facade facade = new FacadeConfig().logFacade(new HsqlDbEventRepositoryAdapter(), Validation.buildDefaultValidatorFactory());
        FilePath filePath = new FilePath(args[0]);

        facade.parse(filePath);

        System.out.println("Program ended.");
    }
}

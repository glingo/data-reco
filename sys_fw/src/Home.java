

import app.AppKernel;
import com.marvin.component.kernel.Kernel;
import com.marvin.console.Console;

public class Home {

    public static void main(String[] args) {

        Kernel kernel = new AppKernel();
        Console console = new Console(kernel);
        console.start();

    }
}

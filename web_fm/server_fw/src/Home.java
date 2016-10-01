

import app.AppKernel;
import com.marvin.component.kernel.Kernel;
import com.marvin.server.Server;

public class Home {

    public static void main(String[] args) {

        Kernel kernel = new AppKernel();
        Server server = new Server(kernel);
        server.start();

    }
}

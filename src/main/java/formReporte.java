import javax.swing.*;
import java.awt.*;

/**
 * Created by Franco on 28/07/2016.
 */

public class formReporte extends JFrame {
        public static void main(String[] args) {
            formReporte frm = new formReporte();
            frm.setDefaultCloseOperation(3);
        }

        public formReporte() {
            super("Reporte");
            Container contenedor = this.getContentPane();
            contenedor.setLayout(new FlowLayout());
            this.setSize(500, 500);
            this.setVisible(true);
        }
}

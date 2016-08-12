import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Franco on 28/07/2016.
 */
public class Principal {
        static double reloj;
        int n;
        int s;
        double ios;
        double dq;
        double db;
        double dd;
        int cli_at;
        double tue;
        int q;
        int d;
        int b;
        double[] le;
        double[] vta;
        ArrayList<Punto> puntosQ;
        ArrayList<Punto> puntosB;
        ArrayList<Punto> puntosD;
        Calendar c = Calendar.getInstance();
        Random r = new Random(c.getTimeInMillis());

    static int infinito = 999999999;


        public Principal() {
        }

        private String imprimir() {
            String rta = " Tue:" + tue + " En Cola:" + n + " Cli_at:" + cli_at + " Dq:" + dq + " Dd:" + dd + " Db:" + db;
            return rta;
        }

        public static void main(String[] args) {
            Principal programa = new Principal();
            programa.inicializar();

            while(reloj <= 40000) {
                char e = programa.tiempos();
                if(e == 'A') {
                    programa.arribo();
                } else if(e=='P'){
                    programa.partida();
                }
                //System.out.println(programa.imprimir());
            }

            System.out.println("Tamaño promedio cola: " + programa.dq / reloj);
            System.out.println("Demora promedio en cola: " + programa.dd / programa.cli_at);
            System.out.println("Utilizacion promedio servidor: " + programa.db / reloj);
            System.out.println(" Fin ");
            programa.reporte();
        }

        public void inicializar() {
            reloj=0;
            q = 0;
            d = 0;
            b = 0;
            n = 0;
            s = 0;
            dq = 0;
            db = 0;
            dd = 0;
            cli_at = 0;
            tue = 0;
            ios = 0;
            puntosQ = new ArrayList<Punto>();
            puntosB=new ArrayList<Punto>();
            puntosD=new ArrayList<Punto>();
            le = new double[2];
            vta = new double[99999];

            for(int i = 0; i <= vta.length - 1; i++) {
                vta[i] = 0;
            }

            le[0] = generarTiempo("Arribo");
            le[1] = infinito;
        }

        public char tiempos() {
            char evento;
            if(le[0] < le[1])
            {
                reloj = le[0];
                evento = 'A';
            }
            else
            {
                reloj = le[1];
                evento = 'P';
            }

            return evento;
        }

        public void arribo() {
            if(s == 0) {
                le[1] = reloj + generarTiempo("Servicio");
                cli_at++;
                ios = reloj;
                s = 1;
            } else {
                double q = (reloj - tue) * n;
                dq=dq+q;
                Punto p = new Punto(reloj,tue,n);
                puntosQ.add(p);
                ++n;

                for(int i = 0; i <= vta.length - 1; i++) {
                    if(vta[i] == 0) {
                        vta[i] = reloj;
                        break;
                    }
                }
            }

            tue = reloj;
            le[0] = reloj + generarTiempo("Arribo");
        }

        public void partida() {

            if(n > 0) {
                le[1] = reloj + generarTiempo("Servicio");
                cli_at++;
                double q = (reloj - tue) * n;
                dq=dq+q;
                Punto p = new Punto(reloj,tue,n);
                puntosQ.add(p);

                double d = reloj - vta[0];
                dd += d;
                Punto pD=new Punto(cli_at,d);
                puntosD.add(pD);
                acomodarVta();
                n--;
            } else {
                s = 0;
                double b = reloj - ios;
                db=db+b;
                Punto p = new Punto(reloj,ios);
                puntosB.add(p);

                le[1] = infinito;
            }

            tue = reloj;
        }



        public double generarTiempo(String tipo) {

            double media = 0;
            double frec=0;
            if(tipo == "Arribo")
            {
                media = 5;
            }
            else if(tipo == "Servicio")
            {
                media = 4;
            }


            double x = -media * Math.log(r.nextDouble());
            return x;


        }

        public void acomodarVta() {
            for(int i = 1; i <= vta.length - 1; i++) {
                vta[i - 1] = vta[i];
            }

        }

        public void reporte() {
            tamañoPromedio();
            demoraPromedio();
            varianza();
            utilizacionPromedio();
        }

        public void demoraPromedio() {

            double[] x0 = new double[puntosD.size()];
            double[] y0 = new double[puntosD.size()];

            double dd=0;

            for(int i = 0; i <= puntosD.size() - 1; i++) {
                int cli=puntosD.get(i).get_cli_at();
                dd+=puntosD.get(i).getD();

                x0[i] = cli;
                y0[i] = (dd / (puntosD.get(i)).get_cli_at());
            }

            ChartLinea grafica = new ChartLinea("Demora Promedio", "Clientes Atendidos", "Demora Promedio", x0, y0, "DemoraPromedio");
            grafica.pack();
            RefineryUtilities.centerFrameOnScreen(grafica);
            grafica.setVisible(true);
        }

        public void varianza() {
            double[] x0=new double[puntosD.size()];
            double[] y0=new double[puntosD.size()];

            double sumad=0;
            int i;
            for(i=0;i<=puntosD.size()-1;i++){
                sumad=sumad+puntosD.get(i).getD();
            }
            double dprom=sumad/cli_at;

            for(i=0;i<=puntosD.size()-1;i++){
                double dif= puntosD.get(i).getD()-dprom;
                x0[i]=puntosD.get(i).get_cli_at();
                y0[i]=(dif*dif)/puntosD.get(i).get_cli_at();
            }


            ChartLinea fc=new ChartLinea("Varianza","Clientes Atendidos","Varianza",x0,y0,"Varianza");
            fc.pack( );
            RefineryUtilities.centerFrameOnScreen( fc );
            fc.setVisible( true );
        }


        public void tamañoPromedio() {
            double tue=0;
            double r=0;
            int n=0;
            int ntotal=0;


            double[] js=new double[puntosQ.size()];
            double[] npp=new double[puntosQ.size()];

            for(int i=0;i<=js.length-1;i++){js[i]=0; npp[i]=0;}

            double dq=0;
            for(int i=0;i<=puntosQ.size()-1;i++){
                r=puntosQ.get(i).getReloj();
                n=puntosQ.get(i).getN();
                tue=puntosQ.get(i).getTue();
                double q= (r-tue)*n;
                dq=dq+q;
                js[i]=r;
                npp[i]=dq/r;
            }

            ChartLinea fc=new ChartLinea("Tamaño Promedio Cola","Tiempo","Promedio",js,npp,"PromedioCola");
            fc.pack( );
            RefineryUtilities.centerFrameOnScreen( fc );
            fc.setVisible( true );
        }

    public void utilizacionPromedio(){
        double[] reloj=new double[puntosB.size()];
        double[] prom=new double[puntosB.size()];

        for(int i=0;i<=reloj.length-1;i++){reloj[i]=0; prom[i]=0;}

        double db=0;
        for(int i=0;i<=puntosB.size()-1;i++){
            reloj[i]=puntosB.get(i).getReloj();
            double b=puntosB.get(i).getReloj()-puntosB.get(i).getIos();
            db+=b;
            prom[i]=db/puntosB.get(i).getReloj();
        }
        ChartLinea fc=new ChartLinea("Utilización Promedio Servidor","Tiempo","Promedio",reloj,prom,"UtilizacionPromedio");
        fc.pack( );
        RefineryUtilities.centerFrameOnScreen( fc );
        fc.setVisible( true );

    }

    public void tamaño() {

        double r=0;
        double[] js=new double[puntosQ.size()];
        double[] npp=new double[puntosQ.size()];

        for(int i=0;i<=js.length-1;i++){js[i]=0; npp[i]=0;}

        double dq=0;
        for(int i=0;i<=puntosQ.size()-1;i++){
            r=puntosQ.get(i).getReloj();
            js[i]=r;
            npp[i]=puntosQ.get(i).getN();
        }

        ChartLinea fc=new ChartLinea("Tamaño Cola","Reloj","Promedio",js,npp,"PromedioCola");
        fc.pack( );
        RefineryUtilities.centerFrameOnScreen( fc );
        fc.setVisible( true );
    }

/*
        public void pj() {


}
*/

    }



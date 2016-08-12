/**
 * Created by Franco on 28/07/2016.
 */

public class Punto {
    private double reloj;
    private int n;
    private int cli_at;
    private double tue;
    private double ios;
    private double d;

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public double getIos() {
        return ios;
    }

    public void setIos(double ios) {
        this.ios = ios;
    }

    public double getTue() {
        return tue;
    }

    public void setTue(double tue) {
        this.tue = tue;
    }

    public double getReloj() {
        return this.reloj;
    }

    public void setReloj(double r) {
        this.reloj = r;
    }

    public int getN() {
        return this.n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int get_cli_at() {
        return this.cli_at;
    }

    public void set_cli_at(int c) {
        this.cli_at = c;
    }

    Punto() {
    }

    Punto(double r,double tue, int nn) {
        //para grafica tamaño promedio Q
        this.setReloj(r);
        this.setTue(tue);
        this.setN(nn);

    }
    Punto(double r,double ios){
        //para grafica utilizacion promedio servidor B
        setReloj(r);
        setIos(ios);
    }
    Punto(int cli_at,double d){
        //para la demora promedio en cola
        set_cli_at(cli_at);
        setD(d);
        }

}



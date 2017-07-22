/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_marvel;

/**
 *
 * @authores David Miguel
 */
public enum TipoFicha {
    HEROE,VILLANO;
    public int r1,r2,r3,r4,r5, r6, r7, r8, r9, r10;
    public static int rango;
    TipoFicha(){
        r1=1;
        r2=2;
        r3=3;
        r4=4;
        r5=5;
        r6=6;
        r7=7;
        r8=8;
        r9=9;
        r10=10;
                
    }

    public int getR1() {
        return r1;
    }

    public int getR2() {
        return r2;
    }

    public int getR3() {
        return r3;
    }

    public int getR4() {
        return r4;
    }
    
    public int getR5() {
        return r5;
    }

    public int getR6() {
        return r6;
    }

    public int getR7() {
        return r7;
    }

    public int getR8() {
        return r8;
    }

    public int getR9() {
        return r9;
    }

    public int getR10() {
        return r10;
    }

    public static int getRango() {
        return rango;
    }
        
}

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class BinPack {
    static long endTime;
    static long startTime;
    static long elapsedTime;

    public static void main(String[] args) {
        int runagain = 0;
        do{
            runagain = driver();
        }
        while (runagain==0);
    }


    public static int driver()
    {
        ArrayList<ArrayList<Double>> binVal = getUserInput();
        int binlength = binVal.size()-1;
        String srep = Arrays.toString(binVal.toArray());
        //System.out.println(" Driver"+Arrays.toString(binVal.toArray()));
        report( srep, binlength);
        return display(binlength);
    }


    public static ArrayList<ArrayList<Double>> getUserInput() // Gets input from the user
    {
        int interlude = 1;
        int binCap;
        int numObj;
        int randVal= -1;
        ArrayList < ArrayList < Double >> solved = new ArrayList < ArrayList < Double >>();
        double[] allValues;
        do {
            binCap = getBinCap();
            JOptionPane.showMessageDialog(null, "BIN CAPACITY set to: " + binCap);
            numObj = getNumObj();
            JOptionPane.showMessageDialog(null, "Working with: " + numObj + " OBJECTS ");
            randVal = askRand();
            if (randVal== 0){
                allValues = allObjValRand(binCap, numObj);
            }
            else{
                allValues = getAllObjVal(binCap, numObj);
            }

            interlude = interlude(binCap, numObj, allValues);
        }
        while (interlude == 1);
        solved = binPack(allValues, numObj, binCap);
        return solved;
    }

    public static double[] allObjValRand(int binCap, int numObj){

        double value = 0;
        double[] allObjVal = new double[numObj];//organizes and assigns all of our values into a DS

        for (int i = 1; i <= numObj; i++) {
            value =  (Math.random() * (binCap - 0.2)) + 0.2;
            allObjVal[i - 1] = value;
        }
        return allObjVal;
    }
    public static int askRand(){
        int askR = JOptionPane.showConfirmDialog(null, "Use randomized object values?\n" +
                "[CHOOSE ONE]\n", null, JOptionPane.YES_NO_OPTION);
        return askR;
    }


    public static ArrayList < ArrayList < Double >> binPack(double weight[], int n, double bin_cap) {
        int binCount = 0;
        int k = 0;
        double[] bin_space = new double[n];

        ArrayList < Integer > bin = new ArrayList < Integer > ();
        ArrayList < Double > val = new ArrayList < Double > ();
        ArrayList < ArrayList < Double >> binVal = new ArrayList < ArrayList < Double >> ();
        binVal.add(k, new ArrayList < Double > ());
        System.out.println(".......Commencing Calculation...........\n");
        startTime = System.nanoTime();
        for (int i = 0; i < n; i++) {
            int j;
            for (j = 0; j < binCount; j++) {

                if (bin_space[j] >= weight[i]) {
                    bin_space[j] = bin_space[j] - weight[i];
                    //val.add(weight[i]);
                    (binVal.get(j)).add((weight[i]));
                    k=j+1;
                    System.out.println("---Bin " + j + " took in weight " + weight[i]);
                    break;
                }

            }
            System.out.println("*Bad fit for next weight size "+ weight[i]+
                    "\n\n ***NEW BIN CREATED***\n");
            if (j == binCount) {

                bin_space[binCount] = bin_cap - weight[i];
                //val.add(weight[i]);
                binCount++;
                //k++;
                bin.add(j);
                binVal.add(j, new ArrayList < Double > ());
                (binVal.get(j)).add((weight[i]));
                k=j+1;
                System.out.println("---Bin " + k + " took in weight **" + weight[i]);
            }
        }
        endTime = System.nanoTime();
        elapsedTime = (endTime- startTime);
        System.out.println("\nBIN PACKING ALGORITHM COMPLETE\n");

        return binVal;
    }

    public static int getNumObj() //Gets the number of objects
    {
        int numObj = -1;
        try {
            if (numObj <= 0) {
                numObj = Integer.parseInt((JOptionPane.showInputDialog("Please enter (POSITIVE) INTEGER for NUMBER-OF-OJECTS:  ")));
                if (numObj <= 0) {
                    throw new NumberFormatException();
                }
            }
        } catch (Exception NumberFormatException) {
            JOptionPane.showMessageDialog(null, "ERROR!!!\n - NUMBER OF OBJECTS MUST BE A (POSITIVE) INTEGER - \n Please try again! \n ", null, JOptionPane.ERROR_MESSAGE);
            numObj = getNumObj(); //recursion
        }

        return numObj;
    }


    public static int getBinCap()
    {
        int binCap = -1;

        try {
            binCap = Integer.parseInt((JOptionPane.showInputDialog("Please enter (POSITIVE) INTEGER for BIN CAPACITY:  ")));
            if (binCap <= 0) {
                throw new NumberFormatException();
            }
        } catch (Exception NumberFormatException) {
            JOptionPane.showMessageDialog(null, "ERROR!!!\n" + "-BIN CAPACITY MUST BE A (POSITIVE) INTEGER-\n" + "Please try again!", null, JOptionPane.ERROR_MESSAGE);
            binCap = getBinCap();
            //recursion
        }
        return binCap;
    }

    public static double getObjVal(int binCap, int numObj, int i) //obtains individual values of our objects
    {
        double objVal = -1;

        try {
            objVal = Double.parseDouble((JOptionPane.showInputDialog("OBJECT #:" + i + " out of " + numObj + "\n Enter INT/DOUBLE (POSITIVE) for next size:")));
            if (objVal <= 0 || objVal > binCap) {
                throw new NumberFormatException();
            }
        } catch (Exception NumberFormatException) {
            JOptionPane.showMessageDialog(null, "ERROR!!!\n - OBJECT SIZE MUST BE POSITIVE - \n - OBJECT SIZE MUST BE INT/DOUBLE - \n -OBJECT SIZE MUST NOT EXCEED BIN CAPACITY:" + binCap +
                    " -\n - Please try again! -  \n ", null, JOptionPane.ERROR_MESSAGE);
            objVal = getObjVal(binCap, numObj, i); //recursion, see what i did here...lol
        }
        return objVal;
    }


    public static double[] getAllObjVal(int binCap, int numObj)
    {
        double value = 0;
        double[] allObjVal = new double[numObj];//organizes and assigns all of our values into a DS
        for (int i = 1; i <= numObj; i++) {
            value = getObjVal(binCap, numObj, i);
            allObjVal[i - 1] = value;
        }
        return allObjVal;
    }


    public static int interlude(int binCap, int numObj, double allValues[]) // confirms the values
    {

        JTextArea msg = new JTextArea("CONFIRMATION OF CHOSEN VALUES:\n\n" +
                "\nBIN-CAPCITY: " + binCap +
                "\nNUMBER-OF-OBJECTS: " + numObj +
                "\nOBJECT SIZES: \n" + Arrays.toString(allValues));
        msg.setLineWrap(true);
        msg.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(msg);
        JOptionPane.showMessageDialog(null, scrollPane);
        int summary = JOptionPane.showConfirmDialog(null,"----------------[PLEASE CHOOSE ONE]----------------\n" +
                "[YES]         - to CONTINUE\n" +
                "[NO]          - to change your values\n" +
                "[CANCEL]    - to EXIT PROGRAM \n" , "CONFIRMATION", JOptionPane.YES_NO_CANCEL_OPTION);
        if (summary == 2) {
            quit();
        }
        return summary;
    }

    /*report() - prints out stastics to the user so that they can see how the bin-packing
               Algorithm went. Prints out to the console for more detail*/
    public static void report (String rep,int numBins ){
        System.out.println("\n\n ..................COMPILING REPORT......................");
        System.out.println("Total Bins used =" + numBins);
        System.out.println("Total time spent =" + elapsedTime +"[M/S]");
        String [] binVal = rep.split("],");
        for( int i =0;i<numBins; i++)
        {
            int j= i+1;
            String binValues =  binVal[i];
            binValues = binValues.replaceAll("[\\[\\](){}]","");
            System.out.println(" Bin "+ j +" Contains values: \n"+ binValues + "\n");
        }
        System.out.println("End of Report..........\n\n");
    }

    public static int display( int binlength){
        JOptionPane.showMessageDialog(null, "\n FOR DETAILED REPORT\nPLEASE CHECK CONSOLE");

        int summary = JOptionPane.showConfirmDialog(null,
                "\n-------------RESULTS----------------\n"+
                        "TOTAL BINS USED : " + binlength + " \n"+
                        "TIME TAKEN: " + elapsedTime + " (M/S) \n" +
                        "\n\n" +"Run NEW test? \n"+
                        "   [CHOOSE ONE]\n" +
                        "[YES]      - to RUN \n" +
                        "[NO]       - to EXIT PROGRAM \n" , "CONFIRMATION" , JOptionPane.YES_NO_OPTION);
        if (summary != 0) {
            quit();
        }
        return summary;
    }


    public static int quit() {
        int quit = JOptionPane.showConfirmDialog(null, "Exit Program? ", null, JOptionPane.YES_NO_OPTION);
        if (quit != 0) {//Confirms Program Termination
            //do nothing
        } else
            System.exit(quit);
        return quit;
    }
}

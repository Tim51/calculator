
import java.util.ArrayList;

public class Calculator {

    public Calculator() {

    }

    public double calculate(String expression) {

        //<editor-fold defaultstate="collapsed" desc="Clean Expression">
        expression = expression.replaceAll(" ", "");
        expression = expression.replaceAll("arcsin", "S");
        expression = expression.replaceAll("arccos", "C");
        expression = expression.replaceAll("arctan", "T");
        expression = expression.replaceAll("sin", "s");
        expression = expression.replaceAll("cos", "c");
        expression = expression.replaceAll("tan", "t");
        expression = expression.replaceAll("sqrt", "r");
        expression = expression.replaceAll("pwr", "^");
        expression = expression.replaceAll("abs", "a");
        expression = expression.replaceAll("pi", ""+Math.PI);
        expression = expression.replaceAll("e", ""+Math.E);
        //</editor-fold>
        
        System.out.println("Simplified");
        
        //<editor-fold defaultstate="collapsed" desc="Split operations and values">
        ArrayList<Double> values = new ArrayList<>();
        ArrayList<String> operations = new ArrayList<>();
        boolean numberNext = true;
        
        for (int index = 0; index < expression.length(); index++) {
            if (isNumber(expression.charAt(index)) && !numberNext) {
                continue;
            }
            if (isNumber(expression.charAt(index))) {
                values.add(nextNumber(expression, index));
                numberNext = false;
            } else {
                //+ , -  means positive or negative number
                if ((expression.charAt(index)=='+'||expression.charAt(index)=='-') 
                                    && (index == 0|| expression.charAt(index-1) == '(')) {
                    values.add(0.);
                    operations.add(nextOperation(expression, index));
                } 
                // multiply brackets e.g. (3)(5)
                else if (expression.charAt(index)=='(' && index != 0) {
                    if (expression.charAt(index-1) == ')' || isNumber(expression.charAt(index-1))) {
                        operations.add("*");   
                    }
                    operations.add("(");
                } 
                //normal
                else { 
                    operations.add(nextOperation(expression, index));
                }
                numberNext = true;
            }
            
        }
        //</editor-fold>
        
        System.out.println(values + "" + operations);
        System.out.println("Splited operations and values");
        
        int pointer = 0;
        int indexOfInteriorOpenBracket = 0;
        String searchingFor = ")";
        int pointerShifter = 0;
        int numberOfLoops = 0;
        
        while (!operations.isEmpty()) {
            
            numberOfLoops++;
            if (numberOfLoops >= 100) {
                return -12321.12321;
            } System.out.println("Loop: " + numberOfLoops);
            
            //<editor-fold defaultstate="collapsed" desc="Parenthesis and PointerShifter">
            while (!searchingFor.equals("nothing")) {
                if (!operations.contains(")")) {
                    searchingFor = "nothing";
                }
                if (searchingFor.equals(")")) {
                    //<editor-fold defaultstate="collapsed" desc="Calculate PointerShifter">
                    if (operations.get(pointer).equals("(")) {
                        pointerShifter++;
                    }
                    if (operations.get(pointer).equals("s")) {
                        pointerShifter++;
                    }
                    if (operations.get(pointer).equals("c")) {
                        pointerShifter++;
                    }
                    if (operations.get(pointer).equals("t")) {
                        pointerShifter++;
                    }
                    if (operations.get(pointer).equals("S")) {
                        pointerShifter++;
                    }
                    if (operations.get(pointer).equals("C")) {
                        pointerShifter++;
                    }
                    if (operations.get(pointer).equals("T")) {
                        pointerShifter++;
                    }
                    if (operations.get(pointer).equals("r")) {
                        pointerShifter++;
                    }
                    if (operations.get(pointer).equals("a")) {
                        pointerShifter++;
                    }
                    //</editor-fold>
                    
                    if (operations.get(pointer).equals(")")) {
                        searchingFor = "(";
                    } else {
                        pointer++;
                    }
                }
                if (searchingFor.equals("(")) {
                    //<editor-fold defaultstate="collapsed" desc="Calculate PointerShifter">
                    if (operations.get(pointer).equals("s")) {
                        pointerShifter--;
                    }
                    if (operations.get(pointer).equals("c")) {
                        pointerShifter--;
                    }
                    if (operations.get(pointer).equals("t")) {
                        pointerShifter--;
                    }
                    if (operations.get(pointer).equals("S")) {
                        pointerShifter--;
                    }
                    if (operations.get(pointer).equals("C")) {
                        pointerShifter--;
                    }
                    if (operations.get(pointer).equals("T")) {
                        pointerShifter--;
                    }
                    if (operations.get(pointer).equals("r")) {
                        pointerShifter--;
                    }
                    if (operations.get(pointer).equals("a")) {
                        pointerShifter--;
                    }
                    //</editor-fold>
                    
                    if (operations.get(pointer).equals("(")) {
                        searchingFor = "nothing";
                        indexOfInteriorOpenBracket = pointer;
                    } else {
                        pointer--;
                    }
                }
            }//</editor-fold>
            System.out.println("PointerShifter: "+pointerShifter);
            
            //<editor-fold defaultstate="collapsed" desc="Power">
            while (operations.contains("^")) {
                if (operations.get(pointer).equals(")")) {
                    pointer = indexOfInteriorOpenBracket;
                    break;
                }
                if (operations.get(pointer).equals("^")) {
                    values.set(pointer-pointerShifter,Math.pow(values.get(pointer-pointerShifter),values.get(pointer + 1-pointerShifter)));
                    values.remove(pointer + 1-pointerShifter);
                    operations.remove(pointer);
                    pointer = indexOfInteriorOpenBracket;
                } else {
                    pointer++;
                }
            }//</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="mod">
            while (operations.contains("%")) {
                if (operations.get(pointer).equals(")")) {
                    pointer = indexOfInteriorOpenBracket;
                    break;
                }
                if (operations.get(pointer).equals("%")) {
                    values.set(pointer-pointerShifter,values.get(pointer-pointerShifter)%values.get(pointer + 1-pointerShifter));
                    values.remove(pointer + 1-pointerShifter);
                    operations.remove(pointer);
                    pointer = indexOfInteriorOpenBracket;
                } else {
                    pointer++;
                }
            }//</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="abs">
            while (operations.contains("a")) {
                if (operations.get(pointer).equals(")")) {
                    pointer = indexOfInteriorOpenBracket;
                    break;
                }
                if (operations.get(pointer).equals("a")) {
                    values.set(pointer-pointerShifter, Math.abs(values.get(pointer-pointerShifter)));
                    operations.remove(pointer);
                    pointer = indexOfInteriorOpenBracket;
                } else {
                    pointer++;
                }
            }//</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="Square root">
            while (operations.contains("r")) {
                if (operations.get(pointer).equals(")")) {
                    pointer = indexOfInteriorOpenBracket;
                    break;
                }
                if (operations.get(pointer).equals("r")) {
                    //Error
                    if (values.get(pointer-pointerShifter) < 0) {
                        return -12321.12321;
                    }
                    values.set(pointer-pointerShifter, Math.sqrt(values.get(pointer-pointerShifter)));
                    operations.remove(pointer);
                    pointer = indexOfInteriorOpenBracket;
                } else {
                    pointer++;
                }
            }//</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="Sin">
            while (operations.contains("s")) {
                if (operations.get(pointer).equals(")")) {
                    pointer = indexOfInteriorOpenBracket;
                    break;
                }
                if (operations.get(pointer).equals("s")) {
                    values.set(pointer-pointerShifter, Math.sin(values.get(pointer-pointerShifter)));
                    operations.remove(pointer);
                    pointer = indexOfInteriorOpenBracket;
                } else {
                    pointer++;
                }
            }//</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="arcsin">
            while (operations.contains("S")) {
                if (operations.get(pointer).equals(")")) {
                    pointer = indexOfInteriorOpenBracket;
                    break;
                }
                if (operations.get(pointer).equals("S")) {
                    values.set(pointer-pointerShifter, Math.asin(values.get(pointer-pointerShifter)));
                    operations.remove(pointer);
                    pointer = indexOfInteriorOpenBracket;
                } else {
                    pointer++;
                }
            }//</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="Cos">
            while (operations.contains("c")) {
                if (operations.get(pointer).equals(")")) {
                    pointer = indexOfInteriorOpenBracket;
                    break;
                }
                if (operations.get(pointer).equals("c")) {
                    values.set(pointer-pointerShifter, Math.cos(values.get(pointer-pointerShifter)));
                    operations.remove(pointer);
                    pointer = indexOfInteriorOpenBracket;
                } else {
                    pointer++;
                }
            }//</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="arccos">
            while (operations.contains("C")) {
                if (operations.get(pointer).equals(")")) {
                    pointer = indexOfInteriorOpenBracket;
                    break;
                }
                if (operations.get(pointer).equals("C")) {
                    values.set(pointer-pointerShifter, Math.acos(values.get(pointer-pointerShifter)));
                    operations.remove(pointer);
                    pointer = indexOfInteriorOpenBracket;
                } else {
                    pointer++;
                }
            }//</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="Tan">
            while (operations.contains("t")) {
                if (operations.get(pointer).equals(")")) {
                    pointer = indexOfInteriorOpenBracket;
                    break;
                }
                if (operations.get(pointer).equals("t")) {
                    values.set(pointer-pointerShifter, Math.tan(values.get(pointer-pointerShifter)));
                    operations.remove(pointer);
                    pointer = indexOfInteriorOpenBracket;
                } else {
                    pointer++;
                }
            }//</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="arctan">
            while (operations.contains("T")) {
                if (operations.get(pointer).equals(")")) {
                    pointer = indexOfInteriorOpenBracket;
                    break;
                }
                if (operations.get(pointer).equals("T")) {
                    values.set(pointer-pointerShifter, Math.atan(values.get(pointer-pointerShifter)));
                    operations.remove(pointer);
                    pointer = indexOfInteriorOpenBracket;
                } else {
                    pointer++;
                }
            }//</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="Division">
            while (operations.contains("/")) {
                if (operations.get(pointer).equals(")")) {
                    pointer = indexOfInteriorOpenBracket;
                    break;
                }
                if (operations.get(pointer).equals("/")) {
                    values.set(pointer-pointerShifter, values.get(pointer-pointerShifter) / values.get(pointer + 1-pointerShifter));
                    values.remove(pointer + 1-pointerShifter);
                    operations.remove(pointer);
                    pointer = indexOfInteriorOpenBracket;
                } else {
                    pointer++;
                }
            }//</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="Multiplication">
            while (operations.contains("*")) {
                if (operations.get(pointer).equals(")")) {
                    pointer = indexOfInteriorOpenBracket;
                    break;
                }
                if (operations.get(pointer).equals("*")) {
                    values.set(pointer-pointerShifter, values.get(pointer-pointerShifter) * values.get(pointer + 1-pointerShifter));
                    values.remove(pointer + 1-pointerShifter);
                    operations.remove(pointer);
                    pointer = indexOfInteriorOpenBracket;
                } else {
                    pointer++;
                }
            }//</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="Subtraction">
            while (operations.contains("-")) {
                if (operations.get(pointer).equals(")")) {
                    pointer = indexOfInteriorOpenBracket;
                    break;
                }
                if (operations.get(pointer).equals("-")) {
                    values.set(pointer-pointerShifter, values.get(pointer-pointerShifter) - values.get(pointer+1-pointerShifter));
                    values.remove(pointer + 1-pointerShifter);
                    operations.remove(pointer);
                    pointer = indexOfInteriorOpenBracket;
                } else {
                    pointer++;
                }
            }//</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="Addition">
            while (operations.contains("+")) {
                if (operations.get(pointer).equals(")")) {
                    pointer = indexOfInteriorOpenBracket;
                    break;
                }
                if (operations.get(pointer).equals("+")) {
                    values.set(pointer-pointerShifter, values.get(pointer-pointerShifter) + values.get(pointer + 1-pointerShifter));
                    values.remove(pointer + 1-pointerShifter);
                    operations.remove(pointer);
                    pointer = indexOfInteriorOpenBracket;
                } else {
                    pointer++;
                }
            }//</editor-fold>
          
            //<editor-fold defaultstate="collapsed" desc="Remove used parenthesis">
            if (operations.contains(")")) {
                operations.remove(pointer);
                operations.remove(indexOfInteriorOpenBracket);
                searchingFor = ")";
                pointer = 0;
                indexOfInteriorOpenBracket = 0;
                pointerShifter = 0;
            }//</editor-fold>
            
            System.out.println(values + "" + operations);
        }
        System.out.println("Finished");
        if (values.size() != 1) {
            return -12321.12321;
        }
        return values.get(0);
    }

    public double nextNumber(String string, int index) {
        int indexOfNextSymbol = string.length();
        for (int i = index; i < string.length(); i++) {
            if (!isNumber(string.charAt(i))) {
                indexOfNextSymbol = i;
                break;
            }
        }
        return Double.parseDouble(string.substring(index, indexOfNextSymbol));
    }

    public String nextOperation(String string, int index) {
        return string.substring(index, index + 1);
    }

    public boolean isNumber(char i) {
        if (i == '0' || i == '1' || i == '2' || i == '3' || i == '4' || i == '5' || i == '6' || i == '7' || i == '8' || i == '9' || i == '0' || i == '.') {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();

        calc.calculate("(3-2*((4/2)-2)+((1)))");
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datext.javanative;
/**
 * This package contains alternative implementations that use java primitive 
 * data types instead of strings for holding data. This provides extremely 
 * fast performance, but takes a performance hit if you change the data type of 
 * a variable. Good for using DaText as a runtime data container where the 
 * structure of the data doesn't change, but the data values need to be able to 
 * change at native hardware speeds (nanoseconds to a microsecond on a typical 
 * desktop computer). 
 */
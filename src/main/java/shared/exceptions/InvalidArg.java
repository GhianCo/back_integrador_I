/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shared.exceptions;

/**
 *
 * @author ghianco
 */
public class InvalidArg extends IllegalArgumentException {
    public InvalidArg(String message) {
        super(message);
    }
}

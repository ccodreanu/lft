package it.nettiva.codreanu.lft.RegExp;

import it.nettiva.codreanu.lft.NFA;

public interface RegExp {
    NFA compile();
}
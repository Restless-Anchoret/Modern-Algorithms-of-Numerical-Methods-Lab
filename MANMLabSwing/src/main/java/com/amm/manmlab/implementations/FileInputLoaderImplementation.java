package com.amm.manmlab.implementations;

import com.amm.manmlab.interfaces.FileInput;
import com.amm.manmlab.interfaces.FileInputLoader;
import java.util.Collections;

public class FileInputLoaderImplementation implements FileInputLoader {

    @Override
    public FileInput loadInputFromFile() {
        return new FileInput(Collections.EMPTY_LIST);
    }

}
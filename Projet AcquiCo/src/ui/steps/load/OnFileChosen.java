package ui.steps.load;

import parser.TransactionList;

import java.io.File;

public interface OnFileChosen {
    TransactionList fileChosen(File selectedFile);
}

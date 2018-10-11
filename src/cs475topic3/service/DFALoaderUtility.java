package cs475topic3.service;

import cs475topic3.domain.FiniteAutomata;
import cs475topic3.domain.Transition;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
* Utility methods for loading a file and parsing it to finite automata, accept states, and a start state
* */
public class DFALoaderUtility
{
   private JFileChooser fileChooser;
   private Logger logger = Logger.getLogger(this.getClass().getName());

   private static final int START_STATE_INDEX = 0;
   private static final int ACCEPT_STATES_INDEX = 1;
   private static final int TRANSITIONS_START_INDEX = 2;
   private static final int TRANSITION_FROM_STATE_COLUMN_INDEX = 0;
   private static final int TRANSITION_LABEL_COLUMN_INDEX = 1;
   private static final int TRANSITION_TO_STATE_COLUMN_INDEX = 2;

   public DFALoaderUtility()
   {
      fileChooser = new JFileChooser();
      // Setting JFileChooser to only accept text files (*.txt)
      FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter(
            "Text Files", "txt");
      fileChooser.setFileFilter(extensionFilter);
   }

   /*
   * Show the "File Chooser" dialogue and return the file that the user selects.
   * @return Optional of the file selected in the dialogue or else return an empty optional if the user exits or fails to
   * pick a file
   * */
   public Optional<File> pickFile()
   {
      int acceptCode = fileChooser.showOpenDialog(null);
      // if a file is chosen then get the selected file
      if (acceptCode == JFileChooser.APPROVE_OPTION)
      {
         return Optional.of(fileChooser.getSelectedFile());
      }
      return Optional.empty();
   }

   /*
   * Takes a file and builds a list of Strings. Each entry to the list represents a line in the file. The entries
   * are also in the order which they appear in the file
   * @param file to be parsed to a list
   * @return list of strings representing lines in the provided file in sequential order. If file can not be found then
   * return an empty list.
   * */
   public List<String> parseFileToList(File file)
   {
      // order dependent list where each entry represents a line from the inputted file
      List<String> lines = new ArrayList<>();
      try
      {
         Scanner scanner = new Scanner(file);
         // iterate through each line of the file and add it to the list of lines
         while (scanner.hasNextLine())
         {
            // pop line and add to file.
            lines.add(scanner.nextLine());
         }
      }
      catch (FileNotFoundException ex)
      {
         logger.log(Level.WARNING, "No file to parse");
      }
      return lines;
   }

   public String getUserInputString()
   {
      return JOptionPane.showInputDialog("Input string");
   }

   public FiniteAutomata getFiniteAtomata(List<String> parsedFileLines, Map<String, Transition> transitions)
   {
      FiniteAutomata finiteAutomata = new FiniteAutomata();
      finiteAutomata.setStartState(getStartState(parsedFileLines));
      finiteAutomata.setAcceptStates(getAcceptStates(parsedFileLines));
      finiteAutomata.setStates(getStatesFromTransitions(transitions));
      return finiteAutomata;
   }

   private List<String> getStatesFromTransitions(Map<String, Transition> transitionMap)
   {
      // using linkedHashSet to avoid duplication
      LinkedHashSet stateHashSet = new LinkedHashSet();
      List<String> states = new ArrayList<>();
      transitionMap.forEach((s, transition) -> {
               stateHashSet.add(transition.getFromState());
               stateHashSet.add(transition.getToState());
            });
      states.addAll(stateHashSet);
      return states;
   }

   public List<Character> getAlphabetFromTransitions(Map<String, Transition> transitionMap)
   {
      LinkedHashSet alphabetHashSet = new LinkedHashSet();
      List<Character> alphabet = new ArrayList<>();
      transitionMap.forEach((s, transition) -> alphabetHashSet.add(transition.getLabel()));
      alphabet.addAll(alphabetHashSet);
      return alphabet;

   }

   private List<String> getAcceptStates(List<String> parsedFileLines)
   {
      String acceptStatesLine = parsedFileLines.get(ACCEPT_STATES_INDEX);
      return Arrays.asList(acceptStatesLine.split(" "));
   }

   private String getStartState(List<String> parseFileLines)
   {
      return parseFileLines.get(START_STATE_INDEX);
   }

   /*
   * Parse transitions and build map where the key is the value of the fromState appended to the label for O(1) access
   * ie. key for qo a q1 transition would be string value of "q0a"
   * @return from state appended to label mapped to a transition
   * */
   public Map<String, Transition> getTransitions(List<String> parsedFileLines)
   {
      Map<String, Transition> transitionMap = new HashMap<>();
      for (int i = TRANSITIONS_START_INDEX; i < parsedFileLines.size(); i++)
      {
         Transition value = parseTransitionFromLine(parsedFileLines.get(i));
         String key = value.getFromState() + String.valueOf(value.getLabel());
         transitionMap.put(key, value);
      }
      return transitionMap;
   }

   private Transition parseTransitionFromLine(String transitionLine)
   {
      // split string on spaces
      List<String> whitespaceSplitString = Arrays.asList(transitionLine.split(" "));

      Transition transition = new Transition();

      transition.setFromState(whitespaceSplitString.get(TRANSITION_FROM_STATE_COLUMN_INDEX));
      transition.setToState(whitespaceSplitString.get(TRANSITION_TO_STATE_COLUMN_INDEX));
      char label = whitespaceSplitString.get(TRANSITION_LABEL_COLUMN_INDEX).charAt(0);
      transition.setLabel(label);
      return transition;
   }


}

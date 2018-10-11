/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs475topic3;

import cs475topic3.domain.FiniteAutomata;
import cs475topic3.domain.Transition;
import cs475topic3.service.DFALoaderUtility;
import cs475topic3.service.DFARunnerService;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author jstauffer
 */
public class DFALauncher
{

   /**
    * Big(O) analysis:
    * File Loading Service: O(n)
    *    parse file to list : O(n)
    *    getTransitions : O(n-2) -> O(n)
    *    getFiniteAutomata : (getTransitions(O(n) + O(n) = O(2n) -> O(n)
    *    getTransitions : O(n)
    * DFA Runner : O(n)
    *    runDfa O(inputString.length) -> O(n)
    *    isAcceptState O(acceptStates.length) -> O(n)
    */
   public static void main(String[] args)
   {
      DFALoaderUtility loaderUtility = new DFALoaderUtility();
      DFARunnerService runnerService = new DFARunnerService();
      Optional<File> fileOptional = loaderUtility.pickFile();
      String inputString = loaderUtility.getUserInputString();
      if (fileOptional.isPresent())
      {
         // split files lines into a list
         List<String> parsedFileStrings = loaderUtility.parseFileToList(fileOptional.get());
         // parse transitions from lines
         Map<String, Transition> transitionMap = loaderUtility.getTransitions(parsedFileStrings);
         // build the finite automata from parsing start state + accept states from lines and add the transitions to the finite automata
         FiniteAutomata finiteAutomata = loaderUtility.getFiniteAtomata(parsedFileStrings, transitionMap);
         // parse the transitions to get the alphabet
         List<Character> alphabet = loaderUtility.getAlphabetFromTransitions(transitionMap);

         try
         {
            // run input string through finite automata. Throw IllegalArgumentException if the user input is an invalid string
            // for the finite automata
            String finalState = runnerService.runFiniteAutomata(inputString, finiteAutomata, transitionMap);
            // check that the final state is an accept state
            boolean finishedInAcceptState = runnerService.isInAcceptState(finiteAutomata.getAcceptStates(), finalState);
            // show results of running the input string through the finite automata
            runnerService.showResults(finishedInAcceptState, alphabet);
         } catch (IllegalArgumentException ex) {
            // show an error dialogue if the input string is invalid
            runnerService.showErrorMsg();
         }

      }
   }


}

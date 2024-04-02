package seedu.duke.command;


import seedu.duke.exceptions.GpaNullException;

public class ViewGpaCommand extends Command{
    @Override
    public void execute(String userInput) {
        try {
            moduleList.tallyGPA();
            System.out.println("Your current GPA is: " + moduleList.getCurrentGPA());
        } catch (GpaNullException e) {
            System.out.println(e.getMessage());
        }
    }
}

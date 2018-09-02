package roman.ironbank.controller.commands;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {

    public Command defineCommand(HttpServletRequest request){
        Command command = null;
        String commandParam = request.getParameter("command");
        if(commandParam == null || commandParam.isEmpty())
            return command;
            try{
                CommandList current = CommandList.valueOf(commandParam.toUpperCase());
                command = current.getCommand();
            }catch (IllegalArgumentException e){
                request.setAttribute("wrong_command",commandParam);
            }
            return command;
    }
}

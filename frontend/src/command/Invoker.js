class Invoker {

    invoke(command) {

        var result = command.execute();
        return result;
    }
}

const invoker = new Invoker();

export default invoker;
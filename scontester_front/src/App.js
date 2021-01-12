import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import './App.css';
import Home from "./components/Home/Home";
import SourceCodeView from "./components/SourceCodeView/SourceCodeView";
import AttemptsView from "./components/AttemptsView/AttemptsView";
import 'bootstrap/dist/css/bootstrap.min.css';
import TaskView from "./components/TaskView/TaskView";

function App() {
  return (
      <Router>
          <Switch>
              <Route path="/code/:id" children={<SourceCodeView />} />
              <Route path="/attempts">
                  <AttemptsView />
              </Route>
              <Route path="/task/:id" children={<TaskView />} />
              <Route path="/">
                  <Home />
              </Route>
          </Switch>
      </Router>
  );
}

export default App;

import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import './App.css';
import Home from "./components/Home/Home";
import SourceCodeView from "./components/SourceCodeView/SourceCodeView";
import AttemptsView from "./components/AttemptsView/AttemptsView";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-toastify/dist/ReactToastify.css';
import TaskView from "./components/TaskView/TaskView";
import Login from "./components/Login/Login";
import AdminPage from "./components/AdminPage/AdminPage";
import {ToastContainer} from "react-toastify";

function App() {
  return (
      <Router>
          <Switch>
              <Route path="/code/:id" children={<SourceCodeView />} />
              <Route path="/attempts">
                  <AttemptsView />
              </Route>
              <Route path="/login">
                  <Login />
              </Route>
              <Route path="/admin">
                  <AdminPage />
              </Route>
              <Route path="/task/:id" children={<TaskView />} />
              <Route path="/">
                  <Home />
              </Route>
          </Switch>
          <ToastContainer
              position="top-right"
              autoClose={5000}
              hideProgressBar={false}
              newestOnTop
              closeOnClick
              rtl={false}
              pauseOnFocusLoss
              draggable
              pauseOnHover
          />
      </Router>
  );
}

export default App;

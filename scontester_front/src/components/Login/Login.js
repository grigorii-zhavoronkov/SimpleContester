import {Button, Form} from "react-bootstrap";
import {useEffect, useState} from "react";
import API from '../api'
import {toast} from "react-toastify";
import {useHistory} from "react-router";


function Login() {

    const history = useHistory();

    const [password, setPassword] = useState('');

    function submit() {
        API.get("admin/api/login", {
            headers: {
                "Authorization": password
            }
        }).then(function(response) {
            if (response.status === 200) {
                localStorage.setItem("Authorization", password);
                toast.success("Успешная авторизация");
                history.push("/admin")
            }
        }).catch(function(err) {
            toast.error("Авторизация не удалась. Неверный пароль/сервер не доступен.")
        })
    }

    useEffect(() => {
        let oldPassword = localStorage.getItem("Authorization");
        if (oldPassword !== null) {
            API.get("admin/api/login", {
                headers: {
                    "Authorization": oldPassword
                }
            }).then(function(response) {
                if (response.status === 200) {
                    history.push("/admin")
                }
            })
        }
    }, [])


    return (
        <Form>
            <Form.Group controlId="password">
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" value={password} onChange={(event => setPassword(event.target.value))}/>
            </Form.Group>
            <Button onClick={submit}>Отправить</Button>
        </Form>
    )
}

export default Login;
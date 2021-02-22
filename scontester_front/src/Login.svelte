<script>
    import {Form, FormGroup, Input, Label, Button, Modal, ModalBody, ModalHeader, ModalFooter} from 'sveltestrap';
    import {push} from 'svelte-spa-router';
    import axios from 'axios'

    let sender = {uid: ''}
    let password = '';
    let showWarning = false;
    const toggle = () => (showWarning = !showWarning);

    function loginAsSender(e) {
        e.preventDefault();
        axios.post("/sender/checkUID", sender)
            .then(r => {
                if (r.status === 200) {
                    localStorage.setItem("UID", sender.uid);
                    push("/tasks")
                } else {
                    showWarning = true
                }
            })
            .catch(e => {
                showWarning = true
            });
    }

    function loginAsAdmin() {
        axios.get("/admin/api/login", {
            headers: {
                "Authorization": password
            }
        })
            .then(r => {
                if (r.status === 200) {
                    localStorage.setItem("password", password);
                    push("/admin");
                }
            })
            .catch(e => {
                showWarning = true;
            })
    }
</script>

<div class="container">
    <h4>Добро пожаловать в систему проверки заданий Simple Contester</h4>
    <Form>
        <FormGroup>
            <Label for="UID">Для получения списка заданий введите свой UID</Label>
            <Input type="text" name="UID" maxlength="5" bind:value={sender.uid} />
        </FormGroup>
        <Button on:click={loginAsSender} color="primary">Войти</Button>
    </Form>
    <br /><br />
    <hr />
    <br />
    <p class="align-middle">или</p>
    <br />
    <hr />
    <br />
    <Form>
        <FormGroup>
            <Label for="password">Введите пароль, чтобы войти как администратор:</Label>
            <Input type="password" name="password" bind:value={password}/>
        </FormGroup>
    </Form>
    <Button on:click={loginAsAdmin} color="primary">Войти</Button>
    <Modal isOpen={showWarning} {toggle}>
        <ModalHeader {toggle}>Ошибка</ModalHeader>
        <ModalBody>Неверный UID/пароль</ModalBody>
        <ModalFooter>
            <Button color="primary" on:click={toggle}>ОК</Button>
        </ModalFooter>
    </Modal>
</div>
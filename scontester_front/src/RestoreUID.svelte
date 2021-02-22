<script>
    let showDialog = false;

    import {Modal, ModalHeader, ModalBody, ModalFooter, Button, FormGroup, Label, Input} from "sveltestrap"

    let sender = {name: ''};

    function restoreUID() {
        toggle();
    }

    const toggle = () => {showDialog = !showDialog}
    // bind:value={showDialog}
</script>

<Modal isOpen={showDialog} {toggle}>
    <ModalHeader {toggle}>Восстановление UID</ModalHeader>
    <ModalBody>
    <FormGroup>
        <Label for="name">Введите свое ФИО: </Label>
        <Input bind:value={sender.name} id="name" type="text" placeholder="Иванов Иван Иванович" />
    </FormGroup>
    </ModalBody>
    <ModalFooter>
        <Button on:click={restoreUID} color="primary">Восстановить</Button>
    </ModalFooter>
</Modal>
<p class="help">Для отправки решений вам нужен специальный идентификатор доступа(UID),
    если Вы его забыли, можете попробовать его <span class="link" on:click={toggle}>восстановить</span></p>

<style>
    .link {
        color: #1d8ef1;
        cursor: pointer;
    }

    .link:hover {
        text-decoration: underline;
    }

    .help {
        position: absolute;
        bottom: 10px;
    }
</style>
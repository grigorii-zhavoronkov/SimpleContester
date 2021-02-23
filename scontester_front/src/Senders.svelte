<script>
    import CheckAdminLogin from "./CheckAdminLogin.svelte";
    import {Table, Spinner, Button, Modal, ModalHeader, ModalFooter, ModalBody, FormGroup, Label, Input} from "sveltestrap";
    import {onMount} from "svelte";
    import axios from "axios";

    let senders = [];
    let newSender = {
        name: ""
    }
    let createSenderModalOpen = false;
    const toggle = () => (createSenderModalOpen = !createSenderModalOpen)

    onMount(() => loadSenders())

    function loadSenders() {
        const password = localStorage.getItem("password");
        if (password) {
            axios.get("admin/api/getSenders", {
                headers: {
                    "Authorization": password
                }
            }).then(r => {
                senders = r.data;
            })
        }
    }

    function createSender() {
        const password = localStorage.getItem("password");
        if (password) {
            axios.post("admin/api/createSender", newSender, {headers: {
                    "Authorization": password
                }})
                .then(r => {
                    newSender = {name: ""}
                    loadSenders();
                })
                .finally(() => toggle())
        }
    }

    function deleteSender(uid) {
        const password = localStorage.getItem("password");
        if (password) {
            axios.post("admin/api/deleteSender", {uid}, {headers: {
                    "Authorization": password
                }})
                .then(r => {
                    loadSenders();
                });
        }
    }

</script>

<div class="container">
    <CheckAdminLogin />
    <Button color="primary" on:click={toggle}>Добавить отправителя</Button>
    <br />
    <br />
    {#if senders.length !== 0}
        <Table bordered>
            <thead>
            <tr>
                <td>UID</td>
                <td>Name</td>
                <td>Удалить</td>
            </tr>
            </thead>
            <tbody>
            {#each senders as sender}
                <tr>
                    <td>{sender.uid}</td>
                    <td>{sender.name}</td>
                    <td><Button color="danger" on:click={deleteSender(sender.uid)}>X</Button></td>
                </tr>
            {/each}
            </tbody>
        </Table>
    {:else}
        <Spinner color="primary" />
    {/if}
</div>
<Modal isOpen={createSenderModalOpen} {toggle}>
    <ModalHeader {toggle}>Создать отправителя</ModalHeader>
    <ModalBody>
        <FormGroup>
            <Label>Введите имя отправителя</Label>
            <Input type="text" bind:value={newSender.name} maxlength="255" />
        </FormGroup>
    </ModalBody>
    <ModalFooter>
        <Button color="primary" on:click={createSender}>Добавить</Button>
        <Button color="danger" on:click={toggle}>Отмена</Button>
    </ModalFooter>
</Modal>
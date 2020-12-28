import { useParams } from "react-router-dom"

function SourceCodeView() {

    let { id } = useParams();

    return (
        <div>
            {id}
        </div>
    )
}

export default SourceCodeView;
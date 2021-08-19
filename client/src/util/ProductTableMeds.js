import React, { Component } from 'react';
import ProductRow from "./ProductRowMeds";

class ProductTableMeds extends Component{
    constructor(props) {
        super(props);
    }

    render() {
        var onProductTableUpdate = this.props.onProductTableUpdate;
        var rowDel = this.props.onRowDel;
        var rowUpdate = this.props.onRowUpdate;
        //var filterText = this.props.filterText;
        var product = this.props.products.map(function(product,index) {
            product.id=index;
            return (<ProductRow onProductTableUpdate={onProductTableUpdate} product={product} onDelEvent={rowDel.bind(this)} key={index} onUpdateEvent={rowUpdate.bind(this)} />)
        });
        return (
            <div>

                <table className="table table-bordered">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Side Efects</th>
                        <th>Dosage</th>
                    </tr>
                    </thead>

                    <tbody>
                    {product}

                    </tbody>

                </table>
            </div>
        );

    }
}

export default ProductTableMeds;
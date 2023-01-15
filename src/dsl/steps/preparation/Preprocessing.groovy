package dsl.steps.preparation

import dsl.steps.DSLThrower
import kernel.StringUtils


class Preprocessing implements DSLThrower {


    def methods = [:]


    void notNull() {
        this.methods.put("notNull", "")
    }


    void removeOutliers(Float q1, Float q3, String... cols) {
        boolean validQ1 = q1 >= 0;
        boolean validQ3 = q3 <= 1;
        boolean validGap = q3 > q1;

        // verification outliers
        if (!validQ1 || !validQ3 || !validGap) {
            def msg = "";
            if (!validQ1)
                msg += "The first quartile should be higher than 0" + StringUtils.lineFeed()
            if (!validQ3)
                msg += "The last quartile should be lower than 1" + StringUtils.lineFeed()
            if (!validGap)
                msg += " Should have q1 < q3" + StringUtils.lineFeed()

            this.reject(msg)
        }
        methods.put("removeOutliers", [q1, q3, cols.length === 0 ? "*" : cols])
    }

    void drop(columnName) {

    }

}

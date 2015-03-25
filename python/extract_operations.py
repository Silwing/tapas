__author__ = 'silwing'

import csv

def extract_operations(srcfile, trgfile):
    operation_result = []
    with open(srcfile, "rb") as results:
        with open(trgfile, "wb") as operations:
            reader = csv.reader(results, delimiter="\t")
            header_row = None

            for row in reader:
                if row[0] == "OperationHandler" and header_row is None:
                    header_row = row
                    continue
                if row[0] == "OperationHandler" and header_row is not None:
                    if row[2] != header_row[2]:
                        print("Names and operation filenames did not match: %s != %s" % (header_row[2], row[2]))
                        header_row = None
                        continue

                    this_file = {}
                    for i in range(3, len(row)):
                        this_file[header_row[i]] = row[i]
                    this_file["filename"] = row[1]
                    operation_result.append(this_file)
                    header_row = None
                    continue

            '''field_list = map(lambda d: set(d.keys()), operation_result)
            fields = list(set.union(*field_list))'''
            field_list = ["filename","array_fill","array_chunk","array_mr_part","array_merge","compact","array_dk_part","array_rand","array_kflip","asort","count","array_walk_rec","array_ms_part","array_pop","array_filter","ksort","array_change_key_case","array_map_result","array_mp_part","extract","array_splice","array_combine_keys","array_intersect_key","current","array_shift","assign_ref","array_merge_rec","array_udiff","array_append","array_search","array_fill_keys","array_key_exists","range","array_dif","shuffle","key","array_values","array_walk","array_sum","usort","array_combine_values","array_count_values","next","array_read","array_unique","prev","sort","array_replace","natcasesort","array_diff_assoc","reset","array_write","array_keys","array_filter_result","krsort","array_slice","array_df_part","array_diff","array_unshift","array_diff_key","array_intersect_assoc","min","array_it_part","array_reverse","array_mr_result","in_array","uasort","array_replace_rec","array_init","assign_tmp","array_push","assign_var","max","array_combine_result","uksort","arsort","end","array_intersect","array_pad","array_reduce","natsort","assign_const"]
            field_list.remove("assign_ref")
            field_list.remove("assign_var")
            field_list.remove("assign_tmp")
            field_list.remove("assign_const")
            field_list.remove("array_read")
            field_list.remove("array_write")
            field_list.remove("array_init")
            writer = csv.DictWriter(operations, field_list, restval=0, extrasaction="ignore", delimiter="\t")
            writer.writeheader()
            writer.writerows(operation_result)


if __name__ == "__main__":
    extract_operations("results.csv", "operations.csv")